<#macro page>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Codeforces</title>
    <link rel="stylesheet" type="text/css" href="/css/normalize.css">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="stylesheet" type="text/css" href="/css/form.css">
    <link rel="stylesheet" type="text/css" href="/css/datatable.css">
</head>
<body>
<header>
    <a href="/"><img src="/img/logo.png" alt="Codeforces" title="Codeforces"/></a>
    <div class="languages">
        <a href="#"><img src="/img/gb.png" alt="In English" title="In English"/></a>
        <a href="#"><img src="/img/ru.png" alt="In Russian" title="In Russian"/></a>
    </div>
    <div class="enter-or-register-box">
        <#if userLogin??>
            ${userLogin}
            |
            <a href="/logout">Logout</a>
        <#else>
            <a href="/enter">Enter</a>
            |
            <a href="/register">Register</a>
        </#if>
    </div>
    <nav>
        <ul>
            <li><a href="/">Home</a></li>
            <li><a href="/top">Top</a></li>
            <li><a href="/contests">Contests</a></li>
            <li><a href="/gym">Gym</a></li>
            <li><a href="/problemset">Problemset</a></li>
            <li><a href="/groups">Groups</a></li>
            <li><a href="/rating">Rating</a></li>
            <#if userLogin??>
                <li><a href="/addNews">Add news</a></li>
            </#if>
        </ul>
    </nav>
</header>
<div class="middle">
    <aside>
        <section>
            <div class="header">
                Pay attention
            </div>
            <div class="body">
                Lorem ipsum dolor sit amet, consectetur adipisicing elit. Cupiditate ducimus enim facere impedit nobis,
                nulla placeat quam suscipit unde voluptatibus.
            </div>
            <div class="footer">
                <a href="#">View all</a>
            </div>
        </section>
        <section>
            <div class="header">
                News
            </div>
            <div class="body">
                <#list newsList as news>
                    <div class="news-login">
                        ${news.userLogin}
                    </div>
                    <div class="news-text">
                        ${news.text}
                    </div>
                </#list>
            </div>
            <div class="footer">
                <a href="#">View all</a>
            </div>
        </section>
    </aside>
    <main>
        <#nested/>
    </main>
</div>
<footer>
    <div class="about-author">
        <a href="#">Codeforces</a> &copy; 2010-2018 by Mike Mirzayanov ${text!}
    </div>
    <div class="user-count">
        <span>Number of registered users: ${userCount!}</span>
    </div>
</footer>
</body>
</html>
</#macro>
