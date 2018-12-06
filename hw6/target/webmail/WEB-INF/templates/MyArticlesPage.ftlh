<#-- @ftlvariable name="articles" type="ru.itmo.webmail.model.domain.Article[]" -->
<#import "common.ftl" as c>

<@c.page>
<div class="datatable user-article">
    <div class="caption">My articles</div>
    <table>
        <thead>
        <tr>
            <th>#</th>
            <th>Title</th>
            <th>Visible</th>
        </tr>
        </thead>
        <tbody>
        <#list articles as article>
        <tr>
            <td class="articleId">
                ${article.id + 1}
            </td>
            <td>
                ${article.title}
            </td>
            <td>
                <div class="button-field">
                    <#if article.isHidden()>
                        <input type="submit" class="hiddenValue" name="hiddenValue" value="Hide">
                    <#else>
                        <input type="submit" class="hiddenValue" name="hiddenValue" value="Show">
                    </#if>
                </div>
            </td>
        </tr>
        </#list>
    </table>
</div>
<script>
    $(function() {
        $(".user-article").on("click", ".hiddenValue", function () {
            var articleId = $(this).parent().parent().parent().find(".articleId").text();
            var articleHiddenStatus = this.value;
            var but = $(this);
            but.val("Processing");
            console.log(articleId + " " + articleHiddenStatus);
            $.post("", {action: "updateHiddenStatus", articleId: articleId, articleHiddenStatus: articleHiddenStatus}, function (response) {
                if (response["success"]) {
                    but.val(response["newStatus"]);
                }
            });
            return false;
        });
    })
</script>
</@c.page>