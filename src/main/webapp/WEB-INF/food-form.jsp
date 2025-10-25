<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Thêm món ăn mới</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h2 class="mb-4">Thêm món ăn mới</h2>

    <form method="post" action="${pageContext.request.contextPath}/foods.jsp">
        <div class="form-group">
            <label>Tên món ăn</label>
            <input type="text" name="name" class="form-control" required minlength="7">
        </div>

        <div class="form-group">
            <label>Mô tả</label>
            <textarea name="description" class="form-control"></textarea>
        </div>

        <div class="form-group">
            <label>Giá bán</label>
            <input type="number" name="price" class="form-control" min="1" required>
        </div>

        <div class="form-group">
            <label>Ảnh món ăn</label><br>
            <input type="hidden" name="imagePath" id="product-thumbnail">
            <button type="button" id="upload_widget" class="btn btn-outline-primary">
                📷 Chọn ảnh sản phẩm
            </button>
            <div class="mt-3">
                <img id="image-preview" src="" alt="" style="max-width: 200px; display:none; border-radius:8px;">
            </div>
        </div>


        <div class="form-group">
            <label>Danh mục món ăn</label>
            <select name="categoryCode" class="form-control" required>
                <option value="">-- Chọn danh mục --</option>
                <c:forEach var="cat" items="${categories}">
                    <option value="${cat.code}">${cat.name}</option>
                </c:forEach>
            </select>
        </div>


        <button type="submit" class="btn btn-success">Thêm món</button>
        <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">Quay lại</a>
    </form>

    <c:if test="${not empty error}">
        <div class="alert alert-danger mt-3">${error}</div>
    </c:if>
</div>
<script src="https://upload-widget.cloudinary.com/latest/global/all.js" type="text/javascript"></script>
<script type="text/javascript">
    var myWidget = cloudinary.createUploadWidget({
            cloudName: 'bemixzyy',
            uploadPreset: 'ml_default'}, (error, result) => {
            if (!error && result && result.event === "success") {
                console.log('Done! Here is the image info: ', result.info.secure_url);
                document.getElementById("product-thumbnail").value = result.info.secure_url;
                document.getElementById("image-preview").src = result.info.secure_url;
                document.getElementById("image-preview").style.display = "block";
            }
        }
    )

    document.getElementById("upload_widget").addEventListener("click", function(){
        myWidget.open();
    }, false);
</script>

</body>
</html>
