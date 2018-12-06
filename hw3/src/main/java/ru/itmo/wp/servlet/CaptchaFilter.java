package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ImageUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Base64;
import java.util.Random;

public class CaptchaFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String captchaAttributeName = "passedCaptcha";
        if (session.getAttribute("uri") == null) {
            session.setAttribute("uri", request.getRequestURI());
        }

        if (request.getParameter("captcha--text") != null && request.getParameter("captcha--text").equals(session.getAttribute("captchaVal"))) {
            session.setAttribute(captchaAttributeName, true);
        }

        if (session.getAttribute(captchaAttributeName) == null || !((boolean) session.getAttribute(captchaAttributeName))) {
            session.setAttribute(captchaAttributeName, false);
            int captchaNumber = generateCaptcha();
            session.setAttribute("captchaVal", Integer.toString(captchaNumber));
            String captchaImage = Base64.getEncoder().encodeToString(ImageUtils.toPng(Integer.toString(captchaNumber)));

            response.getOutputStream().write(getPage(captchaImage).getBytes());
            response.getOutputStream().flush();
            response.setContentType("text/html");
        } else {

            chain.doFilter(request, response);
        }
    }

    private int generateCaptcha() {
        Random randNumber = new Random();
        return randNumber.nextInt(899) + 100;
    }

    private String getPage(String captchaImage) throws IOException {
        String[] res = {"<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Captcha</title>\n" +
                "</head>\n" +
                "<body style=\"width: 100%; height: 100%;\">\n" +
                "    <main style=\"width: 60%;margin: 5rem auto;overflow: auto;\">\n" +
                "        <div class=\"captcha\" style=\"width: 35%;display: flex;flex-direction: column;justify-content: space-around;align-items: center;margin: auto; padding: 10px;border: 1px solid gray;border-radius: 3px;\">\n" +
                "            <div class=\"captcha--header\" style=\"font-family: Letter Gothic Std, sans-serif;font-size: 1.5rem;text-align: center;margin-bottom: 15px;\">\n" +
                "                <span>To get to the page enter the captcha</span>\n" +
                "            </div>\n" +
                "            <img src=\"data:image/png;base64, ",

                "\">\n" +
                "           <form name=\"captchaForm\" method=\"get\">" +
                "                <input type=\"text\" name=\"captcha--text\" style=\"width: 15rem;height: 1.2rem;\">\n" +
                            "</form>" +
                "        </div>\n" +
                "    </main>\n" +
                "</body>\n" +
                "</html>"
        };

        return res[0]  + captchaImage + res[1];
    }
}
