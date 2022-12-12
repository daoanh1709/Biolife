/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.mbeans;

import com.entitybeans.Categories;
import com.entitybeans.Deals;
import com.entitybeans.OrderDetails;
import com.entitybeans.ProductImages;
import com.entitybeans.Products;
import com.sessionbeans.CategoriesFacadeLocal;
import com.sessionbeans.DealsFacadeLocal;
import com.sessionbeans.ProductImagesFacadeLocal;
import com.sessionbeans.ProductsFacadeLocal;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 *
 * @author PC
 */
@Named(value = "productMB")
@SessionScoped
public class ProductsMB implements Serializable {

    @EJB
    private DealsFacadeLocal dealsFacade;

    @EJB
    private ProductImagesFacadeLocal productImagesFacade;

    @EJB
    private CategoriesFacadeLocal categoriesFacade;

    @EJB
    private ProductsFacadeLocal productsFacade;

    private Products product;

    private String formTitle;

    private boolean requiredImage;

    private Part file;

    private String selectedCategory;

    private int pageNumber = 1;

    private String cateID;

    private String sortSelected;

    List<Products> listProductSort = new ArrayList<>();

    private String proID;

    public ProductsMB() {
        product = new Products();
    }

    public double dealPrice(String id) {
        Deals d = dealsFacade.getTodayDealProduct(productsFacade.find(id));
        if (d != null) {
            double unitPrice = d.getProductID().getUnitPrice().doubleValue();
            return unitPrice * (1 - d.getDealDiscount());
        }
        return 0;
    }

    public List<Products> showRelatedProducts(String id) {
        List<Products> listProduct = (List<Products>) categoriesFacade.find(productsFacade.find(id).getCategoryID().getCategoryID()).getProductsCollection();
        List<Products> listRelated = new ArrayList<>();
        for (int i = 0; i < listProduct.size(); i++) {
            if (!listProduct.get(i).getProductID().equals(id)) {
                listRelated.add(listProduct.get(i));
            }
        }

        return listRelated;
    }

    public List<ProductImages> showProductImages(String id) {
        return (List<ProductImages>) productsFacade.find(id).getProductImagesCollection();
    }

    public List<Products> showProductDetails() {
        List<Products> listProduct = new ArrayList<>();
        Products p = productsFacade.find(proID);
        listProduct.add(p);
        return listProduct;
    }

    public List<Products> showBestSellerByCategory(String id) {
        return productsFacade.findBestSeller(categoriesFacade.find(id));
    }

    public boolean checkCurrentPage(int page) {
        if (page == pageNumber) {
            return true;
        } else {
            return false;
        }
    }

    public List<Integer> calculateMaxPageNumber() {
        List<Products> listProduct = new ArrayList<>();
        if (cateID != null) {
            if (cateID.equals("all")) {
                listProduct = productsFacade.findAll();
            } else {
                listProduct = (List<Products>) categoriesFacade.find(cateID).getProductsCollection();
            }
        } else {
            listProduct = productsFacade.findAll();
        }
        int p = (int) Math.ceil((double) listProduct.size() / 7);
        List<Integer> listPage = new ArrayList<>();
        for (int i = 1; i <= p; i++) {
            listPage.add(i);
        }
        return listPage;
    }

    public String changeSort() {
        return "products";
    }

    public List<Products> showProducts() {
        if (sortSelected == null || sortSelected.equals("default")) {
            if (cateID != null) {
                if (cateID.equals("all")) {
                    return productsFacade.showPagination(pageNumber);
                }
                return productsFacade.showCategoryPagination(categoriesFacade.find(cateID), pageNumber);
            }
            return productsFacade.showPagination(pageNumber);
        } else {
//            if (!sortSelected.equals("default")) {
            if (cateID != null) {
                if (cateID.equals("all")) {
                    return productsFacade.showSortProduct(pageNumber, sortSelected);
                } else {
                    return productsFacade.showSortCategoryProduct(categoriesFacade.find(cateID), pageNumber, sortSelected);
                }
            }
            return productsFacade.showSortProduct(pageNumber, sortSelected);
//            }
        }
    }

    public List<Products> showProductByCatgory(String id) {
        List<Products> listProduct = (List<Products>) categoriesFacade.find(id).getProductsCollection();
        return listProduct;
    }

    public String changeFeatured(String id) {
        Products p = productsFacade.find(id);

        if (p.getProductFeatured() == true) {
            p.setProductFeatured(false);
        } else {
            p.setProductFeatured(true);
        }

        productsFacade.edit(p);

        return "productFeatured";
    }

    public List<Products> showUnfeaturedProduct() {
        return productsFacade.showUnfeaturedProduct();
    }

    public List<Products> showFeaturedProduct() {
        return productsFacade.showFeaturedProduct();
    }

    public boolean validDelete(String id) {
        Products p = productsFacade.find(id);
        List<OrderDetails> listOrderDetail = new ArrayList<>();
        listOrderDetail = (List<OrderDetails>) p.getOrderDetailsCollection();
        if (listOrderDetail.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public String deleteProduct(String id) {
        Products p = productsFacade.find(id);

        List<ProductImages> listProductImage = (List<ProductImages>) p.getProductImagesCollection();

        for (int i = 0; i < listProductImage.size(); i++) {
            productImagesFacade.remove(listProductImage.get(i));
        }

        productsFacade.remove(p);

        return "products";
    }

    public String loadUpdateForm(String id) {
        formTitle = "Edit Product";
        requiredImage = false;

        product = productsFacade.find(id);
        selectedCategory = product.getCategoryID().getCategoryID();

        return "productManage";
    }

    public void saveProduct() {
        try {

            Products p = productsFacade.find(product.getProductID());
            if (p == null) {
                product.setProductFeatured(false);
                product.setProductStatus("Enable");
                product.setCategoryID(categoriesFacade.find(selectedCategory));
                product.setProductImageURL("images/product/" + product.getCategoryID().getCategoryName().replaceAll("\\s", "") + "/" + product.getProductID() + ".jpg");
                productsFacade.create(product);
            } else {
                product.setProductFeatured(p.getProductFeatured());
                product.setProductStatus("Enable");
                product.setCategoryID(categoriesFacade.find(selectedCategory));
                product.setProductImageURL("images/product/" + product.getCategoryID().getCategoryName().replaceAll("\\s", "") + "/" + product.getProductID() + ".jpg");
                productsFacade.edit(product);
            }

            uploadFile(product.getProductID(), product.getCategoryID().getCategoryName().replaceAll("\\s", ""));

            product = new Products();

            FacesContext.getCurrentInstance().getExternalContext().redirect("products.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(CategoriesMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void uploadFile(String filename, String folder) {
        String UPLOAD_DIRECTORY = "resources\\images\\product\\" + folder;

        if (file != null) {
            InputStream content = null;
            try {
                content = file.getInputStream();

                FacesContext context = FacesContext.getCurrentInstance();
                ExternalContext ec = context.getExternalContext();
                HttpServletRequest request = (HttpServletRequest) ec.getRequest();

                String applicationPath = request.getServletContext().getRealPath("");
                String uploadFilePath1 = applicationPath + File.separator + UPLOAD_DIRECTORY;
                String uploadFilePath2 = "D:\\School\\EJB\\Assignment\\EJBAssignment\\EJBAssignment-war\\build\\web" + File.separator + UPLOAD_DIRECTORY;
                String uploadFilePath3 = "D:\\School\\EJB\\Assignment\\EJBAssignment\\EJBAssignment-war\\web" + File.separator + UPLOAD_DIRECTORY;

                File fileSaveDir1 = new File(uploadFilePath1);
                if (!fileSaveDir1.exists()) {
                    fileSaveDir1.mkdirs();
                }

                File fileSaveDir2 = new File(uploadFilePath2);
                if (!fileSaveDir2.exists()) {
                    fileSaveDir2.mkdirs();
                }

                File fileSaveDir3 = new File(uploadFilePath3);
                if (!fileSaveDir3.exists()) {
                    fileSaveDir3.mkdirs();
                }

                OutputStream outputStream1 = null;
                OutputStream outputStream2 = null;
                OutputStream outputStream3 = null;
                try {
                    File outputFilePath1 = new File(uploadFilePath1 + File.separator + filename + ".jpg");
                    File outputFilePath2 = new File(uploadFilePath2 + File.separator + filename + ".jpg");
                    File outputFilePath3 = new File(uploadFilePath3 + File.separator + filename + ".jpg");
                    System.out.println(uploadFilePath1 + File.separator + filename + ".jpg");
                    System.out.println(uploadFilePath2 + File.separator + filename + ".jpg");
                    System.out.println(uploadFilePath3 + File.separator + filename + ".jpg");
                    content = file.getInputStream();
                    outputStream1 = new FileOutputStream(outputFilePath1);
                    outputStream2 = new FileOutputStream(outputFilePath2);
                    outputStream3 = new FileOutputStream(outputFilePath3);
                    int read = 0;
                    final byte[] bytes = new byte[1024];
                    while ((read = content.read(bytes)) != -1) {
                        outputStream1.write(bytes, 0, read);
                        outputStream2.write(bytes, 0, read);
                        outputStream3.write(bytes, 0, read);
                    }
                    System.out.println("File uploaded successfully!");
                } catch (Exception ex) {
                    ex.toString();
                } finally {
                    if (outputStream1 != null) {
                        outputStream1.close();
                    }
                    if (outputStream2 != null) {
                        outputStream2.close();
                    }
                    if (outputStream3 != null) {
                        outputStream3.close();
                    }
                    if (content != null) {
                        content.close();
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(ProductsMB.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    content.close();
                } catch (IOException ex) {
                    Logger.getLogger(ProductsMB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public String loadInsertForm() {
        formTitle = "Add New Product";
        requiredImage = true;
        product = new Products();
        product.setProductID(createNewProductID());
        product.setProductImageURL("images/home-04/cat-thumb.png");
        return "productManage";
    }

    public String createNewProductID() {
        List<Products> listProduct = productsFacade.findAll();
        String lastID = listProduct.get(listProduct.size() - 1).getProductID();
        String id;
        int num = Integer.parseInt(lastID.substring(3)) + 1;
        if (num < 10) {
            id = "PRO00" + num;
        } else if (num >= 10 && num < 100) {
            id = "PRO0" + num;
        } else {
            id = "PRO" + num;
        }
        return id;
    }

    public List<Categories> showAllCategories() {
        return categoriesFacade.findAll();
    }

    public List<Products> showAllProducts() {
        return productsFacade.findAll();
    }

    public String backView() {
        return "products";
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public String getFormTitle() {
        return formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    public boolean isRequiredImage() {
        return requiredImage;
    }

    public void setRequiredImage(boolean requiredImage) {
        this.requiredImage = requiredImage;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public String getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getCateID() {
        return cateID;
    }

    public void setCateID(String cateID) {
        this.cateID = cateID;
    }

    public String getSortSelected() {
        return sortSelected;
    }

    public void setSortSelected(String sortSelected) {
        this.sortSelected = sortSelected;
    }

    public String getProID() {
        return proID;
    }

    public void setProID(String proID) {
        this.proID = proID;
    }

}
