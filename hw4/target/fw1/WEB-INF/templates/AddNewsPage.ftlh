<#import "common.ftl" as c>

<@c.page>
    <div class="form-box">
        <div class="header">Enter your note</div>
        <div class="body">
            <form method="post" action="/addNews">
                <input type="hidden" name="action" value="addNews">
                <textarea class="news-text" rows="8" name="text-news"></textarea>
                <div class="button-field">
                    <input type="submit" value="Send">
                </div>
            </form>
        </div>
    </div>
</@c.page>