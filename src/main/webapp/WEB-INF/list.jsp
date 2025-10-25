<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.t2404.restaurant.entity.Food" %>
<%@ page import="com.t2404.restaurant.entity.FoodStatus" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Danh sách món ăn</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .food-card img {
            max-height: 180px;
            object-fit: cover;
        }
        .food-card {
            transition: transform 0.2s ease;
        }
        .food-card:hover {
            transform: scale(1.02);
        }
    </style>
</head>
<body>
<div class="container my-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Danh sách món ăn</h2>
        <a href="${pageContext.request.contextPath}/foods/create" class="btn btn-success">
            + Thêm món ăn
        </a>
    </div>

    <div class="row row-cols-1 row-cols-md-3 g-4">
        <%
            List<Food> foodList = (List<Food>) request.getAttribute("foodList");
            if (foodList != null && !foodList.isEmpty()) {
                for (Food food : foodList) {
        %>
        <div class="col">
            <div class="card food-card h-100 shadow-sm">
                <img src="<%= food.getImagePath() != null ? food.getImagePath() : "https://via.placeholder.com/300x180?text=No+Image" %>"
                     class="card-img-top"
                     alt="<%= food.getName() %>">
                <div class="card-body">
                    <h5 class="card-title text-primary"><%= food.getName() %></h5>
                    <p class="card-text"><%= food.getDescription() != null ? food.getDescription() : "Không có mô tả" %></p>
                    <p class="card-text"><strong>Giá:</strong> <%= food.getPrice() %> VND</p>
                    <p class="card-text"><strong>Mã danh mục:</strong> <%= food.getCategoryCode() %></p>
                    <p class="card-text"><strong>Ngày bắt đầu bán:</strong> <%= food.getStartDate() %></p>
                    <p class="card-text">
                        <strong>Trạng thái:</strong>
                        <span class="badge bg-<%= food.getStatus() == FoodStatus.DANG_BAN ? "success" :
                                                   food.getStatus() == FoodStatus.DUNG_BAN ? "warning" : "secondary" %>">
                            <%= food.getStatus().getDisplayName() %>
                        </span>
                    </p>
                    <div class="d-flex justify-content-end mt-3">
                        <button class="btn btn-danger btn-sm" onclick="confirmDelete('<%= food.getCode() %>', this)">
                            Xoá
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <%
            }
        } else {
        %>
        <div class="col">
            <div class="alert alert-info">Không có món ăn nào để hiển thị.</div>
        </div>
        <%
            }
        %>
    </div>
</div>

<script>
    function confirmDelete(code, button) {
        if (!confirm("Bạn có chắc chắn muốn xoá món ăn này không?")) {
            return;
        }
        fetch('${pageContext.request.contextPath}/foods/delete?code=' + code, {
            method: 'DELETE'
        })
            .then(response => {
                if (response.ok) {
                    const card = button.closest('.col');
                    card.remove();
                } else {
                    alert("Không thể xoá món ăn. Mã lỗi: " + response.status);
                }
            })
            .catch(error => {
                alert("Lỗi khi gửi yêu cầu xoá: " + error);
            });
    }
</script>
</body>
</html>
