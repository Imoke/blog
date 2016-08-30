//判断是否是大写字母
jQuery.validator.addMethod("isUpperCase", function (value, element) {
    var rule = /^[A-Z]+$/;
    return this.optional(element) || rule.test(value);
},"请输入大写字母");


//判断是否是字母和数字
jQuery.validator.addMethod("isLetterAndDigit", function (value, element) {
    var rule = /^[a-zA-Z0-9]+$/;
    return this.optional(element) || rule.test(value);
},"请输入字母或数字");


//判断是否是ip
jQuery.validator.addMethod("isIp", function (value, element) {
    var rule = /^((25[0-5]|2[0-4]\d|[01]?\d\d?)($|(?!\.$)\.)){4}$/;
    return this.optional(element) || rule.test(value);
},"ip格式不正确");