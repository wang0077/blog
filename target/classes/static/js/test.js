    (function () {
        var testId = /'(\S+)'/.exec($('.text-center .btn.btn-primary.btn-xs')[0].onclick.toString())[1]
        var courseId = $("#courseId").val();
        var outlineId = $("#outlineId").val();
        $.ajax({
            type: "POST",
            url: getContextPath() + "/course/play/getOutlineResInfo",
            data: {
                resId: testId,
                resType: 2,
                outlineId: outlineId,
                courseId: courseId,
                sourceFlag: "TEST_ING"
            },
            async: true,
            error: function (request) {
            },
            success: function (data) {

                var testId = $($(".ui-dialog_art-grid .smart-form")[0]).find("input")[0].value;
                var id = $($(".ui-dialog_art-grid .panel.panel-default .btn-group.btn-group-sm")[0]).find("input");

                // alert(data.resInfo.testQuesList[0].testId);
                for (var i = 0; i < 1; i++){
                    var tt = id[i].value.toString();
                    alert(tt);
                    alert(data.resInfo.testQuesList[i].quesAnswer);
                    $.ajax({
                        type: "POST",
                        url: "http://mooc.neumooc.com/course/play/updateTestQues",
                        traditional : true,
                        data: {
                            testId: "AEE7B266738440A0A52AE4F67B096BCC",
                            testQuesId: "3BD07887CB7243A7BDABF7BB1C23B3BA",
                            seconds: 1,

                            // optionSelect: " A、 正确"
                            optionSelect: ["A、中国沦为半殖民地半封建社会","B、中国工人阶级的成长壮大","C、中国工人阶级的成长壮大","D、马克思主义在中国的广泛传播"]
                            // optionSelect: "A、中国沦为半殖民地半封建社会"&optionSelect"B、中国工人阶级的成长壮大"
                            // optionSelect: "C、中国工人阶级的成长壮大",
                            // courseId: courseId
                        },
                    })
                }
            }
        });
    })()



        (function () {
            // var test_Id = /'(\S+)'/.exec($('.text-center .btn.btn-primary.btn-xs')[0].onclick.toString())[1];
            var testId = $($(".ui-dialog_art-grid .smart-form")[0]).find("input")[0].value;
            alert(testId);
            var courseId = $("#courseId").val();
            var outlineId = $("#outlineId").val();
            $.ajax({
                type: "POST",
                url: getContextPath() + "/course/play/getOutlineResInfo",
                data: {
                    resId: testId,
                    resType: 2,
                    outlineId: outlineId,
                    courseId: courseId
                },
                async: true,
                error: function (request) {
                },
                success: function (data) {
                    for (var i = 0; i < data.resInfo.testQuesList.length;i++){
                        var str = data.resInfo.testQuesList[i].quesAnswer;
                        var sss = str.split("<as><a>");
                        sss = sss[1].split("</a></as>");
                        sss = sss[0].split("</a><a>");
                        var testId = $($(".ui-dialog_art-grid .smart-form")[0]).find("input")[0].value;
                        var id = $($(".ui-dialog_art-grid .panel.panel-default .btn-group.btn-group-sm")[0]).find("input");
                        var tt = id[i].value.toString();
                        $.ajax({
                            type: "POST",
                            url: "http://mooc.neumooc.com/course/play/updateTestQues",
                            traditional : true,
                            data: {
                                testId: testId,
                                testQuesId: tt,
                                seconds: 1,
                                optionSelect : sss
                            },
                        })
                    }
                }
            });
        });



(function () {
    var testId = /'(\S+)'/.exec($('.text-center .btn.btn-primary.btn-xs')[0].onclick.toString())[1]
    var courseId = $("#courseId").val();
    var outlineId = $("#outlineId").val();
    $.ajax({
        type: "POST",
        url: getContextPath() + "/course/play/getOutlineResInfo",
        data: {
            resId: testId,
            resType: 2,
            outlineId: outlineId,
            courseId: courseId
        },
        async: true,
        error: function (request) {
        },
        success: function (data) {
            for (var i = 0; i < data.resInfo.testQuesList.length;i++){
                var str = data.resInfo.testQuesList[i].quesAnswer;
                var sss = str.split("<as><a>");
                sss = sss[1].split("</a></as>");
                sss = sss[0].split("</a><a>");
                var testId = $($(".ui-dialog_art-grid .smart-form")[0]).find("input")[0].value;
                var id = $($(".ui-dialog_art-grid .panel.panel-default .btn-group.btn-group-sm")[0]).find("input");
                var tt = id[i].value.toString();
                $.ajax({
                    type: "POST",
                    url: "http://mooc.neumooc.com/course/play/updateTestQues",
                    traditional : true,
                    data: {
                        testId: testId,
                        testQuesId: tt,
                        seconds: 1,
                        optionSelect : sss
                    },
                })

            }
            $.ajax({
                type : "POST",
                url : getContextPath() + "/course/play/submitTest",
                data : {
                    testId : testId,
                    resourceId : data.resInfo.testVO.resourceId,
                    submitTestTag : "",
                },
                // "testId=" + "608D062295FF4544AB712107146BE3A1" + "&resourceId=" + "8C23F05D6D7444FABFF978FCFE670D76",
                // + "&submitTestTag=" + submitTestTag,
                async : false,
                error : function(request) {
                },
                success : function(data) {
                    if (data.isOut != "out") {
                        if (data.RET_CODE == "success") {
                            // 显示测试结果
                            var tpl = $('#testResResultTpl').html();
                            var html = juicer(tpl, data);
                            $("#testDiv").html(html);
                            // 提交完成
                            subTestFlag = 0;
                            // 清空时间
                            $("#testSeconds").val("0");
                            $("#useSecondSum").val("0");
                            // 关闭答题时间计时器
                            timeQuesIndex = 0;
                            clearInterval(timeRun);
                            // 关闭倒计时器时
                            isCount = false;
                            if ($countDown) {
                                $('#timeDownDiv').countdown('stop');
                                $("#timeDownDiv").val("剩余时长：00分钟00秒");
                                $("#timeDownDiv").hide();
                            }
                        } else {
                            subTestFlag = 0;
                            showAlert("系统忙，请稍后再试...");
                        }
                    } else {
                        subTestFlag = 0;
                        showLogin();
                    }
                }
            });
            //submitTestImpl();
        }
    });
})()



    $.ajax({
        type : "POST",
        url : getContextPath() + "/course/play/submitTest",
        data : "testId=" + "608D062295FF4544AB712107146BE3A1" + "&resourceId=" + "8C23F05D6D7444FABFF978FCFE670D76",
            // + "&submitTestTag=" + submitTestTag,
        async : false,
        error : function(request) {
        },
        success : function(data) {
            if (data.isOut != "out") {
                if (data.RET_CODE == "success") {
                    // 显示测试结果
                    var tpl = $('#testResResultTpl').html();
                    var html = juicer(tpl, data);
                    $("#testDiv").html(html);
                    // 提交完成
                    subTestFlag = 0;
                    // 清空时间
                    $("#testSeconds").val("0");
                    $("#useSecondSum").val("0");
                    // 关闭答题时间计时器
                    timeQuesIndex = 0;
                    clearInterval(timeRun);
                    // 关闭倒计时器时
                    isCount = false;
                    if ($countDown) {
                        $('#timeDownDiv').countdown('stop');
                        $("#timeDownDiv").val("剩余时长：00分钟00秒");
                        $("#timeDownDiv").hide();
                    }
                } else {
                    subTestFlag = 0;
                    showAlert("系统忙，请稍后再试...");
                }
            } else {
                subTestFlag = 0;
                showLogin();
            }
        }
    });


    $("#testQuesForm").ajaxSubmit({
        beforeSend : function() {
            showModal("正在提交中...");
        },
        success : function(data) {
            hideModal();
            if ("success" == data.RET_CODE) {
                submitTestImpl();
            } else {
                var error = "测试提交失败";
                $("#tipInfo").addClass("tip-error-info");
                $("#tipInfo").html(emsg + error);
            }
        }
    });


    // <as><a>A、中国沦为半殖民地半封建社会</a><a>
    // B、十月革命开辟的世界无产阶级革命新时代</a><a>
    // C、中国工人阶级的成长壮大</a><a>
    // D、马克思主义在中国的广泛传播</a></as>

data.resInfo.testQuesList.length

var quesList = $($(".ui-dialog_art-grid .panel.panel-default .btn-group.btn-group-sm")[0]).find("input").each(function () {
    var a = this.value.toString()
    alert(a)
});


    var testId = $($(".ui-dialog_art-grid .smart-form")[0]).find("input")[0].value;
    alert(testId)


    var testId = $("#testId").val();
    var testType = $("#testType").val();
    $.ajax({
        type : "POST",
        url : "http://hw-neusoft-edu-cn.portal.neutech.com.cn/hw/exercise/saveoa.do",
        data : {
            qt_id: 3,
            qid: 74193,
            objectiveanswer: "F",
            position: 15,
            eid: 14968,
        },
    })





    $(function () {
        function getParameter(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
            var r = location.search.substr(1).match(reg);
            if (r!=null) return (r[2]); return null;
        }
        var  eid = getParameter("eid");
        // alert(eid);
        var sj = "semester=1&eid=" + eid;

        $.ajax({
            type:'post',
            url:'getQuestList.do',
            data:sj,
            cache:false,
            dataType:'json',
            success : function (data) {
                var string = "";
                var ans = data.list;
                for (var i = 0; i < ans.length;i++){

                    $.ajax({
                        type : "POST",
                        url : "http://hw-neusoft-edu-cn.portal.neutech.com.cn/hw/exercise/saveoa.do",
                        data : {
                            qt_id: ans[i].qt_id,
                            qid: ans[i].qid,
                            objectiveanswer: ans[i].answer,
                            position: i + 1,
                            eid: eid,
                        },
                    })
                }
            },
        });
        window.location.reload()
    });