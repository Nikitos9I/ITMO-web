<#-- @ftlvariable name="message" type="java.lang.String" -->

<#import "common.ftl" as c>
<#import "articleTemplate.ftl" as article>

<@c.page>
    <#if message??>
        <p>${message}</p>
    </#if>
    <div class="article-main">
        <div class="no-data"></div>
    </div>

    <template class="article-template">
        <div class="article-header">
            <div class="title"></div>
        </div>
        <div class="article-body">
            <div class="text"></div>
        </div>
    </template>
    <script>
        $(function () {
            var $noData = $(".no-data");
            $noData.text("Loading...");
            $.post("", {action: "find"}, function (articles) {
                var $articleMain = $(".article-main");
                if (articles.length > 0) {
                    $articleMain.empty();
                    articles.forEach(function (article) {
                        console.log(article.hidden);
                        if (article.hidden) {
                            var $currentArticle = cloneTemplate($("template.article-template"));
                            $currentArticle.find(".title").text(article.title);
                            $currentArticle.find(".text").text(article.text);
                            $articleMain.append($currentArticle);
                        }
                    });
                } else {
                    $noData.text("There is no one article now =(");
                }
            })
        })
    </script>
</@c.page>
