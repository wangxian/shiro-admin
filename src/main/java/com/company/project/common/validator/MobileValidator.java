package com.company.project.common.validator;

import com.company.project.common.annotation.IsMobile;
import com.company.project.common.entity.Regexp;
import com.company.project.common.utils.AdminUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 校验是否为合法的手机号码
 *
 * @author ADMIN
 */
public class MobileValidator implements ConstraintValidator<IsMobile, String> {

    @Override
    public void initialize(IsMobile isMobile) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (StringUtils.isBlank(s)) {
                return true;
            } else {
                String regex = Regexp.MOBILE_REG;
                return AdminUtil.match(regex, s);
            }
        } catch (Exception e) {
            return false;
        }
    }
}
