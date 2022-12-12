/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.mbeans;

import com.entitybeans.Categories;
import com.entitybeans.Products;
import com.sessionbeans.CategoriesFacadeLocal;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 *
 * @author PC
 */
@Named(value = "categoriesMB")
@SessionScoped
public class CategoriesMB implements Serializable {

    @EJB
    private CategoriesFacadeLocal categoriesFacade;

    private Categories categories;

    private Part file;

    private String formTitle;
    
    private boolean requiredImage;

    public CategoriesMB() {
        categories = new Categories();
    }
    
    public String findCategoryName(String id) {
        return categoriesFacade.find(id).getCategoryName();
    }
    
    public int countProductsByCategory(String id) {
        List<Products> listProduct = (List<Products>) categoriesFacade.find(id).getProductsCollection();
        return listProduct.size();
    }
    
    public boolean validDelete(String id) {
        Categories c = categoriesFacade.find(id);
        List<Products> listProduct = new ArrayList<>();
        listProduct = (List<Products>)c.getProductsCollection();
        if (listProduct.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
    
    public String deleteCategory(String id) {
        Categories c = categoriesFacade.find(id);
        
        categoriesFacade.remove(c);
        
        return "categories";
    }

    public String loadUpdateForm(String id) {
        formTitle = "Edit Category";
        requiredImage = false;

        categories = categoriesFacade.find(id);

        return "categoriesManage";
    }

    public void saveCategory() {
        try {
            uploadFile(categories.getCategoryID());

            Categories c = categoriesFacade.find(categories.getCategoryID());

            if (c == null) {
                categories.setCategoryImageURL("images/home-04/" + categories.getCategoryID() + ".jpg");

                categoriesFacade.create(categories);

                categories = new Categories();
            } else {
                categories.setCategoryImageURL(c.getCategoryImageURL());

                categoriesFacade.edit(categories);

                categories = new Categories();
            }

            FacesContext.getCurrentInstance().getExternalContext().redirect("categories.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(CategoriesMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void uploadFile(String filename) {
        String UPLOAD_DIRECTORY = "resources\\images\\home-04";

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
                Logger.getLogger(CategoriesMB.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    content.close();
                } catch (IOException ex) {
                    Logger.getLogger(CategoriesMB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public String backView() {
        return "categories";
    }

    public long instanceDate() {
        return (new Date()).getTime();
    }

    public String createNewCategoryID() {
        List<Categories> listCategories = categoriesFacade.findAll();
        String lastID = listCategories.get(listCategories.size() - 1).getCategoryID();
        String id;
        int num = Integer.parseInt(lastID.substring(3)) + 1;
        if (num < 10) {
            id = "CAT00" + num;
        } else if (num >= 10 && num < 100) {
            id = "CAT0" + num;
        } else {
            id = "CAT" + num;
        }
        return id;
    }

    public String loadInsertForm() {
        formTitle = "Add New Category";
        requiredImage = true;
        categories = new Categories();
        categories.setCategoryID(createNewCategoryID());
        categories.setCategoryImageURL("images/home-04/cat-thumb.png");
        return "categoriesManage";
    }

    public List<Categories> showAllCategories() {
        return categoriesFacade.findAll();
    }

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
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

}
