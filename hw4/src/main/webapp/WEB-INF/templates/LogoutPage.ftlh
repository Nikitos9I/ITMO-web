<#import "common.ftl" as c>

<@c.page>
    <div class="form-box">
        <div class="header">Logout</div>
        <div class="body">
            <form method="post" action="/logout">
                <input type="hidden" name="action" value="logout">
                <div class="field">
                    <div class="logout" style="text-align: center">
                        <span>Do you really want to log out?</span>
                    </div>
                </div>
                <div class="button-field">
                    <input type="submit" value="Logout">
                </div>
            </form>
        </div>
    </div>
</@c.page>