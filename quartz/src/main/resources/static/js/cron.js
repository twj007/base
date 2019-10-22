function everyTime(b) {
    var a = $("input[name=v_" + b.name + "]");
    a.val("*");
    a.change()
}
function unAppoint(d) {
    var a = d.name;
    var c = "?";
    if (a == "year") {
        c = ""
    }
    var b = $("input[name=v_" + a + "]");
    b.val(c);
    b.change()
}
function cycle(e) {
    var b = e.name;
    var c = $(e).parent().find(".numberspinner");
    var f = c.eq(0).numberspinner("getValue");
    var a = c.eq(1).numberspinner("getValue");
    var d = $("input[name=v_" + b + "]");
    d.val(f + "-" + a);
    d.change()
}
function startOn(e) {
    var b = e.name;
    var c = $(e).parent().find(".numberspinner");
    var f = c.eq(0).numberspinner("getValue");
    var a = c.eq(1).numberspinner("getValue");
    var d = $("input[name=v_" + b + "]");
    d.val(f + "/" + a);
    d.change()
}
function lastDay(b) {
    var a = $("input[name=v_" + b.name + "]");
    a.val("L");
    a.change()
}
function weekOfDay(e) {
    var b = e.name;
    var c = $(e).parent().find(".numberspinner");
    var f = c.eq(0).numberspinner("getValue");
    var a = c.eq(1).numberspinner("getValue");
    var d = $("input[name=v_" + b + "]");
    d.val(f + "#" + a);
    d.change()
}
function lastWeek(c) {
    var b = $("input[name=v_" + c.name + "]");
    var a = $(c).parent().find(".numberspinner");
    var d = a.eq(0).numberspinner("getValue");
    b.val(d + "L");
    b.change()
}
function workDay(d) {
    var a = d.name;
    var b = $(d).parent().find(".numberspinner");
    var e = b.eq(0).numberspinner("getValue");
    var c = $("input[name=v_" + a + "]");
    c.val(e + "W");
    c.change()
}
function getQueryString(c, a) {
    var b = new RegExp("(^|&)" + c + "=([^&]*)(&|$)","i");
    if (!a || a == "") {
        a = window.location.search
    } else {
        a = a.substring(a.indexOf("?"))
    }
    r = a.substr(1).match(b);
    if (r != null) {
        return unescape(r[2])
    }
    return null
}
$(function() {
    $(".numberspinner").numberspinner({
        onChange: function() {
            $(this).closest("div.line").children().eq(0).click()
        }
    });
    var h = $("#cron");
    h.on("change", function(e) {

        e.preventDefault();
        e.stopPropagation();
        btnFan();
        $.ajax({
            type: "post",
            url:  "/quartz/job/checkCron",
            dataType: "json",
            data: {
                cron: $("#cron").val()
            },
            success: function(res) {

                var html = '最近10次运行时间: <br>';
                res.forEach(function(d){
                    html += d+'<br>';
                });
                $("#runTime").html(html);
            },
            error: function(e){
                $("#runTime").html(e.responseJSON.message);
            }
        })
    });
    var g = $("input[name^='v_']");
    g.change(function(e) {
        e.stopPropagation();
        var l = [];
        g.each(function() {
            l.push(this.value)
        });
        var j = 0;
        $(".tabs>li").each(function(m, n) {
            if ($(n).hasClass("tabs-selected")) {
                j = m;
                return false
            }
        });
        if (j == 3) {
            if (l[j] == "?") {
                l[j + 2] = "1"
            } else {
                l[j + 2] = "?"
            }
        }
        if (j == 5) {
            if (l[j] == "?") {
                l[j - 2] = "1"
            } else {
                l[j - 2] = "?"
            }
        }
        if (l[3] == "?" && l[5] == "?") {
            l[3] = "1"
        }
        for (var k = j; k >= 1; k--) {
            if ((l[k] != "*" && l[k - 1] == "*") || j == "6") {
                if (k == 4) {
                    l[k - 1] = "1"
                } else {
                    if (k <= 3) {
                        l[k - 1] = "0"
                    }
                }
            }
        }
        if (l[j] == "*") {
            for (var k = j + 1; k < l.length; k++) {
                if (k == 5) {
                    l[k] = "?"
                } else {
                    l[k] = "*"
                }
            }
        }
        h.val(l.join(" ")).change()
    });
    var f = $(".secondList").children();
    $("#sencond_appoint").click(function() {
        if (this.checked) {
            if ($(f).filter(":checked").length == 0) {
                $(f.eq(0)).attr("checked", true)
            }
            f.eq(0).change()
        }
    });
    f.change(function() {
        var k = $("#sencond_appoint").prop("checked");
        if (k) {
            var j = [];
            f.each(function() {
                if (this.checked) {
                    j.push(this.value)
                }
            });
            var l = "?";
            if (j.length > 0 && j.length < 59) {
                l = j.join(",")
            } else {
                if (j.length == 59) {
                    l = "*"
                }
            }
            var i = $("input[name=v_second]");
            i.val(l);
            i.change()
        }
    });
    var b = $(".minList").children();
    $("#min_appoint").click(function() {
        if (this.checked) {
            if ($(b).filter(":checked").length == 0) {
                $(b.eq(0)).attr("checked", true)
            }
            b.eq(0).change()
        }
    });
    b.change(function() {
        var k = $("#min_appoint").prop("checked");
        if (k) {
            var j = [];
            b.each(function() {
                if (this.checked) {
                    j.push(this.value)
                }
            });
            var l = "?";
            if (j.length > 0 && j.length < 59) {
                l = j.join(",")
            } else {
                if (j.length == 59) {
                    l = "*"
                }
            }
            var i = $("input[name=v_min]");
            i.val(l);
            i.change()
        }
    });
    var a = $(".hourList").children();
    $("#hour_appoint").click(function() {
        if (this.checked) {
            if ($(a).filter(":checked").length == 0) {
                $(a.eq(0)).attr("checked", true)
            }
            a.eq(0).change()
        }
    });
    a.change(function() {
        var l = $("#hour_appoint").prop("checked");
        if (l) {
            var j = [];
            a.each(function() {
                if (this.checked) {
                    j.push(this.value)
                }
            });
            var k = "?";
            if (j.length > 0 && j.length < 24) {
                k = j.join(",")
            } else {
                if (j.length == 24) {
                    k = "*"
                }
            }
            var i = $("input[name=v_hour]");
            i.val(k);
            i.change()
        }
    });
    var d = $(".dayList").children();
    $("#day_appoint").click(function() {
        if (this.checked) {
            if ($(d).filter(":checked").length == 0) {
                $(d.eq(0)).attr("checked", true)
            }
            d.eq(0).change()
        }
    });
    d.change(function() {
        var k = $("#day_appoint").prop("checked");
        if (k) {
            var j = [];
            d.each(function() {
                if (this.checked) {
                    j.push(this.value)
                }
            });
            var l = "?";
            if (j.length > 0 && j.length < 31) {
                l = j.join(",")
            } else {
                if (j.length == 31) {
                    l = "*"
                }
            }
            var i = $("input[name=v_day]");
            i.val(l);
            i.change()
        }
    });
    var c = $(".mouthList").children();
    $("#mouth_appoint").click(function() {
        if (this.checked) {
            if ($(c).filter(":checked").length == 0) {
                $(c.eq(0)).attr("checked", true)
            }
            c.eq(0).change()
        }
    });
    c.change(function() {
        var i = $("#mouth_appoint").prop("checked");
        if (i) {
            var k = [];
            c.each(function() {
                if (this.checked) {
                    k.push(this.value)
                }
            });
            var l = "?";
            if (k.length > 0 && k.length < 12) {
                l = k.join(",")
            } else {
                if (k.length == 12) {
                    l = "*"
                }
            }
            var j = $("input[name=v_mouth]");
            j.val(l);
            j.change()
        }
    });
    var e = $(".weekList").children();
    $("#week_appoint").click(function() {
        if (this.checked) {
            if ($(e).filter(":checked").length == 0) {
                $(e.eq(0)).attr("checked", true)
            }
            e.eq(0).change()
        }
    });
    e.change(function() {
        var k = $("#week_appoint").prop("checked");
        if (k) {
            var j = [];
            e.each(function() {
                if (this.checked) {
                    j.push(this.value)
                }
            });
            var l = "?";
            if (j.length > 0 && j.length < 7) {
                l = j.join(",")
            } else {
                if (j.length == 7) {
                    l = "*"
                }
            }
            var i = $("input[name=v_week]");
            i.val(l);
            i.change()
        }
    })
    window.document.getElementById('cron').value = window.parent.document.getElementById("cronExpression").value;
    //$("#cron").val();
    h.trigger("change");
});
function btnFan() {
    var a = $("#cron").val();
    if (a == "") {
        a = "* * * * * ? *";
        $("#cron").val(a).change()
    }
    var b = a.split(" ");
    $("input[name=v_second]").val(b[0]);
    $("input[name=v_min]").val(b[1]);
    $("input[name=v_hour]").val(b[2]);
    $("input[name=v_day]").val(b[3]);
    $("input[name=v_mouth]").val(b[4]);
    $("input[name=v_week]").val(b[5]);
    initObj(b[0], "second");
    initObj(b[1], "min");
    initObj(b[2], "hour");
    initDay(b[3]);
    initMonth(b[4]);
    initWeek(b[5]);
    if (b.length > 6) {
        $("input[name=v_year]").val(b[6]);
        initYear(b[6])
    }
}
function initObj(c, d) {
    var b = null;
    var e = $("input[name='" + d + "'");
    if (c == "*") {
        e.eq(0).attr("checked", "checked")
    } else {
        if (c.split("-").length > 1) {
            b = c.split("-");
            e.eq(1).attr("checked", "checked");
            $("#" + d + "Start_0").numberspinner("setValue", b[0]);
            $("#" + d + "End_0").numberspinner("setValue", b[1])
        } else {
            if (c.split("/").length > 1) {
                b = c.split("/");
                e.eq(2).attr("checked", "checked");
                $("#" + d + "Start_1").numberspinner("setValue", b[0]);
                $("#" + d + "End_1").numberspinner("setValue", b[1])
            } else {
                e.eq(3).attr("checked", "checked");
                if (c != "?") {
                    b = c.split(",");
                    for (var a = 0; a < b.length; a++) {
                        $("." + d + "List input[value='" + b[a] + "']").attr("checked", "checked")
                    }
                }
            }
        }
    }
}
function initDay(c) {
    var b = null;
    var d = $("input[name='day']");
    if (c == "*") {
        d.eq(0).attr("checked", "checked")
    } else {
        if (c == "?") {
            d.eq(1).attr("checked", "checked")
        } else {
            if (c.split("-").length > 1) {
                b = c.split("-");
                d.eq(2).attr("checked", "checked");
                $("#dayStart_0").numberspinner("setValue", b[0]);
                $("#dayEnd_0").numberspinner("setValue", b[1])
            } else {
                if (c.split("/").length > 1) {
                    b = c.split("/");
                    d.eq(3).attr("checked", "checked");
                    $("#dayStart_1").numberspinner("setValue", b[0]);
                    $("#dayEnd_1").numberspinner("setValue", b[1])
                } else {
                    if (c.split("W").length > 1) {
                        b = c.split("W");
                        d.eq(4).attr("checked", "checked");
                        $("#dayStart_2").numberspinner("setValue", b[0])
                    } else {
                        if (c == "L") {
                            d.eq(5).attr("checked", "checked")
                        } else {
                            d.eq(6).attr("checked", "checked");
                            b = c.split(",");
                            for (var a = 0; a < b.length; a++) {
                                $(".dayList input[value='" + b[a] + "']").attr("checked", "checked")
                            }
                        }
                    }
                }
            }
        }
    }
}
function initMonth(c) {
    var b = null;
    var d = $("input[name='mouth']");
    if (c == "*") {
        d.eq(0).attr("checked", "checked")
    } else {
        if (c.split("-").length > 1) {
            b = c.split("-");
            d.eq(2 - 1).attr("checked", "checked");
            $("#mouthStart_0").numberspinner("setValue", b[0]);
            $("#mouthEnd_0").numberspinner("setValue", b[1])
        } else {
            if (c.split("/").length > 1) {
                b = c.split("/");
                d.eq(3 - 1).attr("checked", "checked");
                $("#mouthStart_1").numberspinner("setValue", b[0]);
                $("#mouthEnd_1").numberspinner("setValue", b[1])
            } else {
                d.eq(4 - 1).attr("checked", "checked");
                b = c.split(",");
                for (var a = 0; a < b.length; a++) {
                    $(".mouthList input[value='" + b[a] + "']").attr("checked", "checked")
                }
            }
        }
    }
}
function initWeek(c) {
    var b = null;
    var d = $("input[name='week']");
    if (c == "*") {
        d.eq(0).attr("checked", "checked")
    } else {
        if (c == "?") {
            d.eq(1).attr("checked", "checked")
        } else {
            if (c.split("-").length > 1) {
                b = c.split("-");
                d.eq(2).attr("checked", "checked");
                $("#weekStart_0").numberspinner("setValue", b[0]);
                $("#weekEnd_0").numberspinner("setValue", b[1])
            } else {
                if (c.split("#").length > 1) {
                    b = c.split("#");
                    d.eq(3).attr("checked", "checked");
                    $("#weekStart_1").numberspinner("setValue", b[0]);
                    $("#weekEnd_1").numberspinner("setValue", b[1])
                } else {
                    if (c.split("L").length > 1) {
                        b = c.split("L");
                        d.eq(4).attr("checked", "checked");
                        $("#weekStart_2").numberspinner("setValue", b[0])
                    } else {
                        d.eq(5).attr("checked", "checked");
                        b = c.split(",");
                        for (var a = 0; a < b.length; a++) {
                            $(".weekList input[value='" + b[a] + "']").attr("checked", "checked")
                        }
                    }
                }
            }
        }
    }
}
function initYear(b) {
    var a = null;
    var c = $("input[name='year']");
    if (b == "*") {
        c.eq(1).attr("checked", "checked")
    } else {
        if (b.split("-").length > 1) {
            a = b.split("-");
            c.eq(2).attr("checked", "checked");
            $("#yearStart_0").numberspinner("setValue", a[0]);
            $("#yearEnd_0").numberspinner("setValue", a[1])
        }
    }
}
;