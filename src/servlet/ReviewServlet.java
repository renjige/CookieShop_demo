package servlet;

import model.Review;
import service.ReviewService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 评价提交 Servlet
 * URL: /review
 */
@WebServlet(name = "ReviewServlet", urlPatterns = "/review")
public class ReviewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ReviewService reviewService = new ReviewService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 读取表单参数
        int goodsId = Integer.parseInt(request.getParameter("goodsId"));
        int userId = Integer.parseInt(request.getParameter("userId"));
        int rating = Integer.parseInt(request.getParameter("rating"));
        String comment = request.getParameter("comment");

        // 构建 Review 对象
        Review review = new Review();
        review.setGoodsId(goodsId);
        review.setUserId(userId);
        review.setRating(rating);
        review.setComment(comment);

        // 保存评价
        reviewService.addReview(review);

        // 提交后重定向到详情 Servlet，以加载并显示所有评价
        response.sendRedirect("goods_detail?id=" + goodsId);
    }
}
