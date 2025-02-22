<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<jsp:useBean id="viewedProducts" type="java.util.LinkedList" scope="request"/>
<tags:master pageTitle="Product List">
  <p>
    Cart: ${cart}
  </p>
  <c:if test="${not empty param.message}">
    <span class="success">${param.message}</span>
  </c:if>
  <c:if test="${not empty error}">
    <span class="error">${error}</span>
  </c:if>
  <p>
    ${product.description}
  </p>
  <form method="post">
      <table>
          <tr>
            <td>Image</td>
            <td>
              <img src="${product.imageUrl}">
            </td>
          </tr>
          <tr>
            <td>Code</td>
            <td>
              ${product.code}
            </td>
          </tr>
          </tr>
          <tr>
              <td>Stock</td>
              <td>
                ${product.stock}
              </td>
          </tr>
          </tr>
          <tr>
              <td>Price</td>
              <td class="price">
                <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
              </td>
          </tr>
          <tr>
          <td>Quantity</td>
          <td>
            <input name="quantity" value="${not empty error? param.quantity: 1}" class= "quantity">
          </td>
          </tr>
      </table>
      <button>Add to cart</button>
  </form>
  <div strong>Recently viewed</div>

  <c:forEach var="product" items="${viewedProducts}">
        <tr>
          <td>
            <img class="product-tile" src="${product.imageUrl}">
          </td>
          <td>
              <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                  ${product.description}
              </a>
          </td>
          <td class="price">
              <a>
                  <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
              </a>
          </td>
        </tr>
  </c:forEach>
</tags:master>