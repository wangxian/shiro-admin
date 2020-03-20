package io.webapp.common.service;

import io.webapp.common.entity.AdminConstant;
import io.webapp.common.entity.ImageType;
import io.webapp.common.exception.AdminException;
import io.webapp.common.properties.AdminProperties;
import io.webapp.common.properties.ValidateCodeProperties;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 验证码服务
 *
 * @author ADMIN
 */
@Service
@RequiredArgsConstructor
public class ValidateCodeService {

    private final RedisService redisService;
    private final AdminProperties properties;


    public void create(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String key = session.getId();
        ValidateCodeProperties code = properties.getCode();
        setHeader(response, code.getType());

        Captcha captcha = createCaptcha(code);
        redisService.set(AdminConstant.CODE_PREFIX  + key, StringUtils.lowerCase(captcha.text()), code.getTime());
        captcha.out(response.getOutputStream());
    }


    public void check(String key, String value) throws AdminException {
        Object codeInRedis = redisService.get(AdminConstant.CODE_PREFIX + key);
        if (StringUtils.isBlank(value)) {
            throw new AdminException("请输入验证码");
        }
        if (codeInRedis == null) {
            throw new AdminException("验证码已过期");
        }
        if (!StringUtils.equalsIgnoreCase(value, String.valueOf(codeInRedis))) {
            throw new AdminException("验证码不正确");
        }
    }

    private Captcha createCaptcha(ValidateCodeProperties code) {
        Captcha captcha;
        if (StringUtils.equalsIgnoreCase(code.getType(), ImageType.GIF)) {
            captcha = new GifCaptcha(code.getWidth(), code.getHeight(), code.getLength());
        } else {
            captcha = new SpecCaptcha(code.getWidth(), code.getHeight(), code.getLength());
        }
        captcha.setCharType(code.getCharType());
        return captcha;
    }

    private void setHeader(HttpServletResponse response, String type) {
        if (StringUtils.equalsIgnoreCase(type, ImageType.GIF)) {
            response.setContentType(MediaType.IMAGE_GIF_VALUE);
        } else {
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
        }
        response.setHeader(HttpHeaders.PRAGMA, "No-cache");
        response.setHeader(HttpHeaders.CACHE_CONTROL, "No-cache");
        response.setDateHeader(HttpHeaders.EXPIRES, 0L);
    }
}
