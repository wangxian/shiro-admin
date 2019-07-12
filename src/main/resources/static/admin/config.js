layui.define(function(exports) {
  exports('conf', {
    container: 'admin',
    containerBody: 'admin-body',
    v: '2.0',
    base: layui.cache.base,
    css: layui.cache.base + 'css/',
    views: layui.cache.base + 'views/',
    viewLoadBar: true,
    debug: layui.cache.debug,
    name: 'admin',
    entry: '/index',
    engine: '',
    eventName: 'admin-event',
    tableName: 'admin',
    requestUrl: './'
  })
});
