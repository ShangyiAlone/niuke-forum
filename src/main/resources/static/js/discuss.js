$(function(){
    $("#topBtn").click(setTop);
    $("#wonderfulBtn").click(setWonderful);
    $("#deleteBtn").click(setDelete);
});

function like(btn, entityType, entityId, entityUserId, postId) {
    $.post(
        CONTEXT_PATH + "/like",
        {"entityType":entityType,"entityId":entityId,"entityUserId":entityUserId,"postId":postId},
        function(data) {
            data = $.parseJSON(data);
            if(data.code == 0) {
                $(btn).children("i").text(data.likeCount);
                $(btn).children("b").text(data.likeStatus==1?'已赞':"赞");
            } else {
                alert(data.msg);
            }
        }
    );
}

// 分享
function share(btn, entityType, entityId, entityUserId, postId) {
    let shareUrl;
    $.get(
        CONTEXT_PATH + "/share"+"?htmlUrl=http://localhost:8080/community/discuss/detail/"+postId,
        {"entityType":entityType,"entityId":entityId,"entityUserId":entityUserId,"postId":postId},
        function(data) {
            data = $.parseJSON(data);
            if(data.code == 0) {
                shareUrl=data.shareUrl;

                window.location.href=shareUrl;
                setTimeout(function (){
                    flush(shareUrl);
                },2000);
            } else {
                alert(data.msg);
            }
        }
    );
}
// 延时刷新
function flush(shareUrl) {
    window.location.href=shareUrl;
}

// 置顶
function setTop() {
    $.post(
        CONTEXT_PATH + "/discuss/top",
        {"id":$("#postId").val()},
        function(data) {
            data = $.parseJSON(data);
            if(data.code == 0) {
                $("#topBtn").attr("disabled", "disabled");
            } else {
                alert(data.msg);
            }
        }
    );
}

// 加精
function setWonderful() {
    $.post(
        CONTEXT_PATH + "/discuss/wonderful",
        {"id":$("#postId").val()},
        function(data) {
            data = $.parseJSON(data);
            if(data.code == 0) {
                $("#wonderfulBtn").attr("disabled", "disabled");
            } else {
                alert(data.msg);
            }
        }
    );
}

// 删除
function setDelete() {
    $.post(
        CONTEXT_PATH + "/discuss/delete",
        {"id":$("#postId").val()},
        function(data) {
            data = $.parseJSON(data);
            if(data.code == 0) {
                location.href = CONTEXT_PATH + "/index";
            } else {
                alert(data.msg);
            }
        }
    );
}