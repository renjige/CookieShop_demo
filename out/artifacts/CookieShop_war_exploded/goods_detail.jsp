<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
	<title>商品详情</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link type="text/css" rel="stylesheet" href="css/bootstrap.css">
	<link type="text/css" rel="stylesheet" href="css/style.css">
	<link type="text/css" rel="stylesheet" href="css/flexslider.css">
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/jquery.flexslider.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="layer/layer.js"></script>
	<script type="text/javascript" src="js/cart.js"></script>
	<script>
		$(function() {
			$('.flexslider').flexslider({
				animation: "slide",
				controlNav: "thumbnails"
			});
		});
	</script>
	<style>
		/* 评价部分样式优化 */
		.review-section {
			background-color: #f9f9f9;
			border: 1px solid #ddd;
			border-radius: 5px;
			padding: 20px;
			margin-top: 20px;
		}
		.review-form label {
			font-weight: bold;
		}
		.review-list {
			margin-top: 20px;
		}
		.review-item {
			background-color: #fff;
			border: 1px solid #eee;
			border-radius: 5px;
			padding: 15px;
			margin-bottom: 15px;
		}
		.review-item .rating {
			color: #F07818;
			font-size: 1.2em;
		}
		.review-item .comment {
			margin-top: 10px;
		}
		.review-item .created-at {
			color: #999;
			font-size: 0.9em;
		}
	</style>
</head>
<body>

<!--header-->
<jsp:include page="/header.jsp"></jsp:include>
<!--//header-->

<!--//single-page-->
<div class="single">
	<div class="container">
		<div class="single-grids">
			<div class="col-md-4 single-grid">
				<div class="flexslider">
					<ul class="slides">
						<li data-thumb="${g.cover}">
							<div class="thumb-image"> <img src="${g.cover}" data-imagezoom="true" class="img-responsive"> </div>
						</li>
						<li data-thumb="${g.image1}">
							<div class="thumb-image"> <img src="${g.image1}" data-imagezoom="true" class="img-responsive"> </div>
						</li>
						<li data-thumb="${g.image2}">
							<div class="thumb-image"> <img src="${g.image2}" data-imagezoom="true" class="img-responsive"> </div>
						</li>
					</ul>
				</div>
			</div>
			<div class="col-md-4 single-grid simpleCart_shelfItem">
				<h3>${g.name}</h3>
				<div class="tag">
					<p>分类 : <a href="goods_list?typeid=${g.type.id}">${g.type.name}</a></p>
				</div>
				<p>${g.intro}</p>
				<div class="galry">
					<div class="prices">
						<h5 class="item_price">¥ ${g.price}</h5>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="btn_form">
					<a href="javascript:;" class="add-cart item_add" onclick="buy(${g.id})">加入购物车</a>
				</div>
			</div>
			<div class="col-md-4 single-grid1">
				<!-- <h2>商品分类</h2> -->
				<ul>
					<li><a  href="/goods_list">全部系列</a></li>
					<c:forEach items="${typeList}" var="t">
						<li><a href="/goods_list?typeid=${t.id}">${t.name}</a></li>
					</c:forEach>
				</ul>
			</div>
			<div class="clearfix"> </div>
		</div>
	</div>
</div>

<!-- 评价表单 -->
<div class="container review-section">
	<c:if test="${not empty sessionScope.user}">
		<form action="review" method="post" class="review-form">
			<input type="hidden" name="goodsId" value="${g.id}">
			<input type="hidden" name="userId" value="${sessionScope.user.id}">
			<div class="form-group">
				<label for="rating">星级评价：</label>
				<div class="rating">
					<span data-value="5">★</span>
					<span data-value="4">★</span>
					<span data-value="3">★</span>
					<span data-value="2">★</span>
					<span data-value="1">★</span>
					<input type="hidden" id="rating" name="rating" value="5">
				</div>
			</div>
			<div class="form-group">
				<label for="comment">评价内容：</label>
				<textarea class="form-control" name="comment" id="comment" rows="4" placeholder="请输入您的评价内容"></textarea>
			</div>
			<button type="submit" class="btn btn-primary">提交评价</button>
		</form>
		<script>
			$(document).ready(function() {
				$('.rating span').hover(function() {
					var value = $(this).data('value');
					$('.rating span').each(function() {
						if ($(this).data('value') <= value) {
							$(this).css('color', '#F07818');
						} else {
							$(this).css('color', '');
						}
					});
				}, function() {
					var selectedValue = $('#rating').val();
					$('.rating span').each(function() {
						if ($(this).data('value') <= selectedValue) {
							$(this).css('color', '#F07818');
						} else {
							$(this).css('color', '');
						}
					});
				}).click(function() {
					var value = $(this).data('value');
					$('#rating').val(value);
				});
			});
		</script>
	</c:if>
	<c:if test="${empty sessionScope.user}">
		<p>请先登录后再进行评价。</p>
	</c:if>
</div>

<!-- 显示评价列表 -->
<div class="container review-section review-list">
	<h3>商品评价</h3>

	<c:if test="${not empty reviews}">
		<c:forEach items="${reviews}" var="review">
			<div class="review-item" style="position: relative;">
				<!-- 删除按钮：管理员或作者可见，固定右上角 -->
				<c:if test="${not empty sessionScope.user
	                   && (sessionScope.user.username == 'admin'
	                       || sessionScope.user.id == review.userId)}">
					<a href="delete_review?reviewId=${review.id}&goodsId=${g.id}&commentOwnerId=${review.userId}"
					   class="btn btn-danger btn-sm"
					   style="position:absolute; top:10px; right:10px;"
					   onclick="return confirm('确定删除该评论吗？');">
						删除
					</a>
				</c:if>

				<!-- 星级 -->
				<div class="rating">
					<c:forEach begin="1" end="${review.rating}">
						★
					</c:forEach>
				</div>
				<!-- 用户名 -->
				<div class="username">
					<strong>用户：</strong>${review.username}
				</div>
				<!-- 评论内容 -->
				<div class="comment">
						${review.comment}
				</div>
				<!-- 格式化后的评价时间 -->
				<div class="created-at">
					<strong>评价时间：</strong>
					<fmt:formatDate value="${review.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</div>
			</div>
		</c:forEach>
	</c:if>

	<c:if test="${empty reviews}">
		<p class="text-muted">暂无评价。</p>
	</c:if>
</div>

<!--related-products-->
<!--
<div class="related-products">
    <div class="container">
        <h3>猜你喜欢</h3>
        <div class="product-model-sec single-product-grids">
            <div class="product-grid single-product">
                <a href="single.html">
                <div class="more-product"><span> </span></div>
                <div class="product-img b-link-stripe b-animate-go  thickbox">
                    <img src="images/m1.png" class="img-responsive" alt="">
                    <div class="b-wrapper">
                    <h4 class="b-animate b-from-left  b-delay03">
                    <button>View</button>
                    </h4>
                    </div>
                </div>
                </a>
                <div class="product-info simpleCart_shelfItem">
                    <div class="product-info-cust prt_name">
                        <h4>Product #1</h4>
                        <span class="item_price">$2000</span>
                        <div class="ofr">
                          <p class="pric1"><del>$2300</del></p>
                          <p class="disc">[15% Off]</p>
                        </div>
                        <div class="clearfix"> </div>
                    </div>
                </div>
            </div>
            <div class="clearfix"> </div>
        </div>
    </div>
</div>
-->
<!--related-products-->

<!--footer-->
<jsp:include page="/footer.jsp"></jsp:include>
<!--//footer-->

</body>
</html>