// function addAnswer(e) {
//     console.log("click button");
//     e.preventDefault();
//     console.log(this);
//     var queryString = $('.answer-write').serialize();
//     console.log('[queryString] = ' +queryString);
// }
// $('.answer-write input[type=submit]').click(addAnswer);

$(document).ready(function(){
$('.answer-write input[type=submit]').click(addAnswer);

    function addAnswer(e) {
        console.log("click button");
        e.preventDefault();
        console.log(this);
        var queryString = $('.answer-write').serialize();
        console.log('[queryString] ' +queryString);

        var url = $('.answer-write').attr('action');
        console.log("[url] ", url);

        $.ajax({
            type : 'post',
            url : url,
            data : queryString,
            dataType : 'json',
            error : onError,
            success : onSuccess
        });
    }

    function onError() {
        console.log("ERROR!!" + status);
    }

    function onSuccess(data, status) {
        console.log(data);
        var $answerTemplate = $('#answerTemplate').html();
        var template = $answerTemplate.format(data.writer.userId, data.formattedCreateDate, data.contents, data.question.id, data.id);
        $('.qna-comment-slipp-articles').prepend(template);

        $('.answer-write textarea').val('');//flush!
    }


    $('.qna-comment-slipp-articles').on('click', deleteAnswer);

    function deleteAnswer(e) {
        e.preventDefault();
        var $deleteBtn = $(this).find('.link-delete-article');
        var url = $deleteBtn.attr('href');
        $.ajax({
            url : url,
            type : 'DELETE',
            dataType : 'json',
            error : onError,
            success : function (data, status) {//delete 요청이 성공적으로 이루어지고 서버로부터 응답을 받을 때 실행되는 함수이며 서버로부터 오는 데이터는 data로 받을 수 있다.
                console.log(data);
                console.log(status);
                if (data.valid) {
                    $deleteBtn.closest("article").remove();
                } else {
                    alert(data.errorMessage);
                }
            }
        });
    }

    String.prototype.format = function() {
        var args = arguments;
        return this.replace(/{(\d+)}/g, function(match, number) {
            return typeof args[number] != 'undefined'
                ? args[number]
                : match
                ;
        });
    };
});