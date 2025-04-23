package dao;

import model.Review;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import utils.DataSourceUtils;

import java.sql.SQLException;
import java.util.List;

public class ReviewDao {

    /** 新增评论 */
    public void addReview(Review review) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "INSERT INTO review (goods_id, user_id, rating, comment) VALUES (?,?,?,?)";
        qr.update(sql,
                review.getGoodsId(),
                review.getUserId(),
                review.getRating(),
                review.getComment()
        );
    }

    /** 根据商品 ID 查询评论（带用户名+时间） */
    public List<Review> getReviewsByGoodsId(int goodsId) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        // 显式别名 created_at -> createdAt，username -> username
        String sql = ""
                + "SELECT "
                + "  r.id, "
                + "  r.goods_id      AS goodsId, "
                + "  r.user_id       AS userId, "
                + "  r.rating, "
                + "  r.comment, "
                + "  r.created_at    AS createdAt, "
                + "  u.username      AS username "
                + "FROM review r "
                + "JOIN user u ON r.user_id = u.id "
                + "WHERE r.goods_id = ? "
                + "ORDER BY r.created_at DESC";
        return qr.query(sql, new BeanListHandler<>(Review.class), goodsId);
    }
    /**
     * 根据评论 ID 删除一条评论
     */
    public void deleteReview(int reviewId) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "DELETE FROM review WHERE id = ?";
        qr.update(sql, reviewId);
    }
}
