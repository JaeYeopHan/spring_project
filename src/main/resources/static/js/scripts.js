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
        e.preventDefault();
        var queryString = $('.answer-write').serialize();
        var url = $('.answer-write').attr('action');

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


    $('.qna-comment-slipp-articles').on('click', '.link-delete-article', deleteAnswer);

    function deleteAnswer(e) {
        e.preventDefault();
        var $deleteBtn = $(this);
        var url = $deleteBtn.attr('href');
        $.ajax({
            url : url,
            type : 'DELETE',
            dataType : 'json',
            error : onError,
            success : function (data, status) {
                if (data.valid) {
                    $deleteBtn.closest("article").remove();
                } else {
                    alert(data.message);
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