<#import "/spring.ftl" as spring>
<#import "./common.ftlh" as c>

<#macro error field>
    <@spring.bind field/>
    <#list spring.status.errorMessages as error>
        <div class="error">${error}</div>
        <#break/>
    </#list>
</#macro>

<@c.page>
<div class="notice">
    <#if noSuchNotice??>
        <div class="text">No such notice</div>
    <#else>
        <div class="id"><b>Id</b>: ${notice.id}</div>
        <div class="text"><b>Text</b>: ${notice.text}</div>
        <div class="creation" style="font-size: 20px"><b>Creation</b>: ${notice.creationTime}</div>
        <div class="tags" style="color: var(--border-color);"><br>Tags:
            <ul>
                <#list tags as tag>
                    <li>${tag.name}</li>
                </#list>
            </ul>
        </div>
        <h2 style="text-align: center; margin-top: 1rem;">Comments</h2>
        <#list comments as comment>
            <div class="comments">
                <div class="id">By ${comment.author.login}</div>
                <div class="text" style="font-size: 12px">${comment.text}</div>
                <div class="creation">${comment.creationTime}</div>
            </div>
        </#list>

        <div class="form-box" style="margin-top: 1rem">
            <div class="header">New Comment</div>
            <div class="body">
                <form method="post" action="/notice/${notice.id}">
                    <div class="field">
                        <div class="name">
                            <label for="text">Text</label>
                        </div>
                        <div class="value">
                            <textarea id="text" rows="10" name="text">${addComment.text!?html}</textarea>
                        </div>
                        <@error 'addComment.text'/>
                        <@error 'addComment.author'/>
                    </div>

                    <div class="button-field">
                        <input type="submit" value="Add">
                    </div>
                </form>
            </div>
        </div>
    </#if>
</div>
</@c.page>