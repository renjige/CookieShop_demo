package servlet;

import model.User;
import service.ReviewService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "DeleteReviewServlet", urlPatterns = "/delete_review")
public class DeleteReviewServlet extends HttpServlet {
    private ReviewService reviewService = new ReviewService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 获取参数
        int reviewId = Integer.parseInt(request.getParameter("reviewId"));
        int goodsId  = Integer.parseInt(request.getParameter("goodsId"));

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        boolean allowed = false;
        if (user != null) {
            // 管理员 admin 可以删除任意评论；普通用户只能删除自己的评论
            if ("admin".equals(user.getUsername())) {
                allowed = true;
            } else {
                // 取出评论所属用户 id （需要在 JSP 渲染时带出，或在此再查一次 ReviewService）
                int commentOwnerId = Integer.parseInt(request.getParameter("commentOwnerId"));
                allowed = (user.getId() == commentOwnerId);
            }
        }

        if (allowed) {
            reviewService.deleteReview(reviewId);
        }
        // 删除后重定向回商品详情页
        response.sendRedirect("goods_detail?id=" + goodsId);
    }
}
