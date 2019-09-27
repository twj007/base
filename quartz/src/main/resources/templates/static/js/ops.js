$.extend({
    ops(action, jobKey) {
        console.log(action);
        var status = $("#scheduler").attr("attr");
        if(status != "running"){
            alert("调度器已关闭，请先开启调度器");
            return;
        }
        switch(action){
            case "pause":
                $.ajax({
                    url: "/quartz/job/pause/"+jobKey,
                    success: function(data){
                        alert(data);
                    }
                });
                break;
            case "resume":
                $.ajax({
                    url: "/quartz/job/start/"+jobKey,
                    success: function(data){
                        alert(data);
                    }
                });
                break;
            case "del":
                $.ajax({
                    url: "/quartz/job/delete/"+jobKey,
                    success: function(data){
                        alert(data);
                    }
                });
                break;
            default:
                alert("非法操作！");
                break;
        }
    }
});