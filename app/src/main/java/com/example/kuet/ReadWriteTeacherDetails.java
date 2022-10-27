package com.example.kuet;

public class ReadWriteTeacherDetails {
    public String Name,Department,Hall,HomeTown,DOB,BG,Mobile,Email,Password;


    public ReadWriteTeacherDetails(String txtName,String txtDepartment,String txtHome,String txtdoB,String txtBG,String txtMobile,String txtEmail,String txtPassword){

        this.Name=txtName;
        this.Department=txtDepartment;
        this.HomeTown=txtHome;
        this.DOB=txtdoB;
        this.BG=txtBG;
        this.Mobile=txtMobile;
        this.Email=txtEmail;
        this.Password=txtPassword;
    }
}
