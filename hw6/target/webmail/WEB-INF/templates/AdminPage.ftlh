<#import "common.ftl" as c>

<@c.page>
<div class="datatable admins">
    <div class="caption">Users</div>
    <table>
        <thead>
        <tr>
            <th>#</th>
            <th>Login</th>
            <th>Is Admin</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td colspan="3" class="no-data">No data</td>
        </tr>
        </tbody>
    </table>
</div>

<template class="users-tr">
    <tr>
        <td class="id">{{#}}</td>
        <td class="login">{{login}}</td>
        <td class="isAdmin">{{isAdmin}}</td>
    </tr>
</template>

<template class="change-privilege-header">
    <th class="change-privilege-th">Change Privilege</th>
</template>

<template class="change-privilege-table">
    <td class="change-privilege">
        <input type="submit" class="change-privilege-input" name="change-privilege-input">
    </td>
</template>

<script>
    $(function () {
        var isCurrentUserAdmin = false;
        var $tableHeader = $(".admins thead tr");
        $.post("", {action: "checkIsAdmin"}, function (ans) {
            if (ans) {
                isCurrentUserAdmin = true;
                var $trElem = cloneTemplate($("template.change-privilege-header"));
                $tableHeader.append($trElem);
            }
        });
        var $noData = $(".admins .no-data");
        $noData.text("Loading...");
        $.post("", {action: "find"}, function (users) {
            var $tbody = $(".admins tbody");
            if (users.length > 0) {
                $tbody.empty();
                users.forEach(function (user) {
                    var $tr = cloneTemplate($("template.users-tr"));
                    $tr.find(".id").text(user.id);
                    $tr.find(".login").text(user.login);
                    $tr.find(".isAdmin").text(user.admin? "YES" : "NO");
                    if (isCurrentUserAdmin) {
                        var $trElemBody = cloneTemplate($("template.change-privilege-table"));
                        if (user.admin)
                            $trElemBody.find(".change-privilege-input").val("disable");
                        else {
                            $trElemBody.find(".change-privilege-input").val("enable");
                        }
                        $tr.find("tr").append($trElemBody);
                    }
                    $tr.find("tr").attr("id", "user_" + user.login.toLowerCase());
                    $tbody.append($tr);
                });
            } else {
                $noData.text("No data");
            }
        })
    });
    $(function() {
        $(".admins").on("click", ".change-privilege-input", function () {
            var userId = $(this).parent().parent().find(".id").text();
            var $isAdminField = $(this).parent().parent().find(".isAdmin");
            var userAdminStatus = this.value;
            var but = $(this);
            but.val("Processing");
            console.log(userId + " " + userAdminStatus);
            $.post("", {action: "changePrivilege", userId: userId, userAdminStatus: userAdminStatus}, function (response) {
                if (response["success"]) {
                    $isAdminField.text(response["newStatus"] === "enable"? "NO" : "YES");
                    but.val(response["newStatus"]);
                }

                $.post("", {action: "checkIsAdmin"}, function (ans) {
                    console.log(ans);
                    if (!ans) {
                        var $tableHeader = $(".admins thead tr");
                        var $tableHeaderPriv = $tableHeader.find(".change-privilege-th");
                        $tableHeaderPriv.remove();
                        var $tableBody = $(".admins tbody tr");
                        var $tableBodyPriv = $tableBody.find(".change-privilege");
                        $tableBodyPriv.remove();
                    }
                });
            });
            return false;
        });
    })
</script>
</@c.page>
