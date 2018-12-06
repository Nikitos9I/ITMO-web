<#import "common.ftl" as c>

<@c.page>
    <div class="article form-box">
        <div class="header">Enter your article</div>
        <div class="body">
            <form method="post" action="/article">
                <input class="article-title" type="text" name="article-title" placeholder="Enter title">
                <textarea class="article-text" rows="8" name="article-text" placeholder="Enter text"></textarea>
                <div class="button-field">
                    <input type="submit" value="Add">
                </div>
                <div class="error"></div>
            </form>
        </div>
    </div>
    <script>
        $(function() {
            $(".article form").submit(function () {
                var title = $(this).find("input[name='article-title']").val();
                var text = $(this).find("textarea[name='article-text']").val();
                var $error = $(this).find(".error").text("");
                $.post("", {action: "article", title: title, text: text}, function (response) {
                    if (response["success"]) {
                        document.location.href = "/";
                    } else {
                        $error.text(response["error"]);
                    }
                });
                return false;
            });
        })
    </script>
</@c.page>