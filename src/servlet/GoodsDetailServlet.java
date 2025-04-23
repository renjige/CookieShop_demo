package servlet;

import model.Goods;
import service.GoodsService;
import service.ReviewService;
import model.Review;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 商品详情 Servlet
 * URL: /goods_detail?id={goodsId}
 */
@WebServlet(name = "goods_detail", urlPatterns = "/goods_detail")
public class GoodsDetailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private GoodsService gService = new GoodsService();
    private ReviewService reviewService = new ReviewService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 获取商品 ID
        int id = Integer.parseInt(request.getParameter("id"));
        // 查询商品信息
        Goods g = gService.getGoodsById(id);
        request.setAttribute("g", g);

        // 查询商品评价列表
        List<Review> reviews = reviewService.getReviewsByGoodsId(id);
        request.setAttribute("reviews", reviews);

        // 转发到 JSP 展示
        request.getRequestDispatcher("/goods_detail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // POST 请求转发到 GET，保持逻辑一致
        doGet(request, response);
    }
}

