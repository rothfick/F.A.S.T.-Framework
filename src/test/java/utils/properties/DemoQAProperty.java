package utils.properties;

import utils.PropertyFile;
import utils.IPropertyGet;

@PropertyFile("properties/demoQa.properties")
public enum DemoQAProperty implements IPropertyGet {
    USERNAME,
    USERNAME_ID,
    PASSWORD,
    PASSWORD_ID,

    UPLOAD_PATH,

    HOME_URL,

    DROPDOWN_OPTION,

    VISIBLE_AFTER,

    //FORM
    FIRSTNAME_ID,
    LASTNAME_ID,
    USEREMAIL_ID,
    USERNUMBER_ID,
    SUBJECT_ID,
    UPLOADPICTURE_ID,
    DATE_ID,

    //TABLE
    ADD_NEW_RECORD_BUTTON_ID,
    FIRST_NAME_ID,
    LAST_NAME_ID,
    USER_EMAIL_ID,
    AGE_ID,
    SALARY_ID,
    DEPARTMENT_ID,
    BUTTON_SUBMIT_TABLE_ID,


    BOOK_STORE_APPLICATION_XPATH,
    LOGIN_SECTION_XPATH,
    LOGIN_ID,
    USERNAME_LABEL_ID,
    SUBMIT_BUTTON_ID,
    FORM_SUBMISSION_ID,

    ELEMENTS_TILE_XPATH,
    LINK_TILE_XPATH,
    FORM_TILE_XPATH,
    ALERTSFRAMEWINDOWS_TILE_XPATH,
    WIDGETS_TILE_XPATH,
    BUTTONS_SECTION_XPATH,
    ALERTS_SECTION_XPATH,
    PRACTISE_FORM_SECTION_XPATH,
    WEB_TABLES_SECTION_XPATH,
    DYNAMICPROPERTIES_SECTION_XPATH,

    SELECTMENU_SECTION_XPATH,
    BUTTON_CLICKME_XPATH,
    DROPDOWN_ID,
    ALERT_BUTTON_ID,

    FOOTER_ID

}
