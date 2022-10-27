package com.example.kuet;

public class ReadWriteUserDetails {
    public String Name,Roll,Department,Hall,HomeTown,College,DOB,BG,Mobile,Email,Password;


    public ReadWriteUserDetails(String txtName,String txtRoll,String txtDepartment,String txtHall,String txtHome,String txtCollege,String txtdoB,String txtBG,String txtMobile,String txtEmail,String txtPassword){

        this.Name=txtName;
        this.Roll=txtRoll;
        this.Department=txtDepartment;
        this.Hall=txtHall;
        this.HomeTown=txtHome;
        this.College=txtCollege;
        this.DOB=txtdoB;
        this.BG=txtBG;
        this.Mobile=txtMobile;
        this.Email=txtEmail;
        this.Password=txtPassword;
    }
}
