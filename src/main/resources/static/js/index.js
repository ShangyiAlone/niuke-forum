// var E = window.wangEditor
// var editor = new E('#div1')
// //设置上传的参数名
// editor.customConfig.uploadFileName = 'file';
// // 上传图片到服务器
// editor.customConfig.uploadImgServer = 'http://localhost:8080/community/discuss/uploadFile'
// // 将图片大小限制为 10M
// editor.customConfig.uploadImgMaxSize = 10 * 1024 * 1024;
// editor.create()

// $(function(){
// 	$("#publishBtn").click(publish);
// });
//
// function publish() {
//
// 	// 获取标题和内容
// 	var title = $("#recipient-name").val();
// 	// var content = $("#message-text").val();
// 	var content = editor.txt.html()
//
// 	// 发送异步请求(POST)
// 	$.post(
// 	    CONTEXT_PATH + "/discuss/uploadFile",
// 	    {"title":title,"content":content},
// 	    function(data) {
// 	        data = $.parseJSON(data);
// 	        // 在提示框中显示返回消息
// 	        $("#hintBody").text(data.msg);
// 	        // 显示提示框
//             $("#hintModal").modal("show");
//             // 2秒后,自动隐藏提示框
//             setTimeout(function(){
//                 $("#hintModal").modal("hide");
//                 // 跳转回首页
//                 if(data.code == 0) {
//                     window.location.href= CONTEXT_PATH + "/index";
//                 }
//             }, 2000);
// 	    }
// 	);
// }
