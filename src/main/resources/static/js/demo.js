function post(questionId, parentId, contentContainer) {
    let content = contentContainer.val();
    if (!content) {
        alert("不能回复空内容");
        return
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "questionId": questionId,
            "parentId": parentId,
            "content": content
        }),
        success: function (response) {
            if (response === 200) {
                // Success
                contentContainer.val("");
                window.location.reload();
            }
            // console.log(response);
        },
        dataType: "json"
    });
}

function postComment() {
    let questionId = $("#question_id").val();
    let contentContainer = $("#comment_content");
    post(questionId, null, contentContainer);
}

function postSubComment(questionId, parentId) {
    let contentContainer = $("#comment_input_" + parentId);
    post(questionId, parentId, contentContainer);
}

function incLike(e, id, type) {
    let liked = e.classList.contains("active");
    let typeId = id;
    $.ajax({
        type: "POST",
        url: "/like",
        contentType: "application/json",
        data: JSON.stringify({
            "likeId": id,
            "likeType": type,
            "liked": liked
        }),
        success: function (response) {
            var id = 'like_' + type + '_' + typeId
            console.log(id)
            let element = document.getElementById(id);
            oldCount = parseInt(element.innerHTML);
            console.log(response)
            if (response === 200) {
                // Success
                e.classList.toggle("active");
                element.innerHTML = oldCount + 1;
            } else if (response === 202) {
                e.classList.toggle("active");
                element.innerHTML = oldCount - 1;
            }
        },
        error: function (response) {
            console.log(response)
            if (response.status == 401) {
                window.location.href = "/login"
            }
        },
        dataType: "json"
    });
}

function getSubComment(e, id) {
    let comments = $("#collapse_" + id);
    let collapse = e.getAttribute("data-collapse");
    if (collapse) {
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        $.getJSON("/comment/" + id, function (data) {
            let subContainer = $("#sub_container_" + id);
            if (subContainer.children().length === 0) {
                $.each(data, function (index, comment) {

                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object media-avatar img-rounded",
                        "src": comment.user.headUrl
                    }));

                    let name = comment.user.username
                    if (comment.user.nickname != null) {
                        name = comment.user.nickname
                    }

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<span/>", {
                        "class": "media-heading text-name",
                        "html": name
                    })).append($("<img/>", {
                        "style": "width: 30px",
                        "src": comment.user.grade.iconUrl
                    })).append($("<br>")).append($("<span/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "text-name a-grey"
                    }).append($("<span/>", {
                        "style": "font-size: 12px;",
                        "html": moment(comment.createTime).format('YYYY-MM-DD')
                    })));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    subContainer.append(mediaElement);
                });
            }
            comments.addClass("in");
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        });
    }
}
