package com.team1.team1project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class testController {
    @GetMapping("/")
    public String testPage() {
        return "dist/index";
    }
    @GetMapping("/table")
    public String tabletestPage() {
        return "dist/table";
    }

    @GetMapping("/account-profile")
    public String accountProfile() {
        return "dist/account-profile"; // account-profile.html
    }

    @GetMapping("/account-security")
    public String accountSecurity() {
        return "dist/account-security"; // account-security.html
    }

    @GetMapping("/application-chat")
    public String applicationChat() {
        return "dist/application-chat"; // application-chat.html
    }

    @GetMapping("/application-checkout")
    public String applicationCheckout() {
        return "dist/application-checkout"; // application-checkout.html
    }

    @GetMapping("/application-email")
    public String applicationEmail() {
        return "dist/application-email"; // application-email.html
    }

    @GetMapping("/application-gallery")
    public String applicationGallery() {
        return "dist/application-gallery"; // application-gallery.html
    }

    @GetMapping("/auth-forgot-password")
    public String authForgotPassword() {
        return "dist/auth-forgot-password"; // auth-forgot-password.html
    }

    @GetMapping("/auth-login")
    public String authLogin() {
        return "dist/auth-login"; // auth-login.html
    }

    @GetMapping("/auth-register")
    public String authRegister() {
        return "dist/auth-register"; // auth-register.html
    }

    @GetMapping("/component-accordion")
    public String componentAccordion() {
        return "dist/component-accordion"; // component-accordion.html
    }

    @GetMapping("/component-alert")
    public String componentAlert() {
        return "dist/component-alert"; // component-alert.html
    }

    @GetMapping("/component-badge")
    public String componentBadge() {
        return "dist/component-badge"; // component-badge.html
    }

    @GetMapping("/component-breadcrumb")
    public String componentBreadcrumb() {
        return "dist/component-breadcrumb"; // component-breadcrumb.html
    }

    @GetMapping("/component-button")
    public String componentButton() {
        return "dist/component-button"; // component-button.html
    }

    @GetMapping("/component-card")
    public String componentCard() {
        return "dist/component-card"; // component-card.html
    }

    @GetMapping("/component-carousel")
    public String componentCarousel() {
        return "dist/component-carousel"; // component-carousel.html
    }

    @GetMapping("/component-collapse")
    public String componentCollapse() {
        return "dist/component-collapse"; // component-collapse.html
    }

    @GetMapping("/component-dropdown")
    public String componentDropdown() {
        return "dist/component-dropdown"; // component-dropdown.html
    }

    @GetMapping("/component-list-group")
    public String componentListGroup() {
        return "dist/component-list-group"; // component-list-group.html
    }

    @GetMapping("/component-modal")
    public String componentModal() {
        return "dist/component-modal"; // component-modal.html
    }

    @GetMapping("/component-navs")
    public String componentNavs() {
        return "dist/component-navs"; // component-navs.html
    }

    @GetMapping("/component-pagination")
    public String componentPagination() {
        return "dist/component-pagination"; // component-pagination.html
    }

    @GetMapping("/component-placeholder")
    public String componentPlaceholder() {
        return "dist/component-placeholder"; // component-placeholder.html
    }

    @GetMapping("/component-progress")
    public String componentProgress() {
        return "dist/component-progress"; // component-progress.html
    }

    @GetMapping("/component-spinner")
    public String componentSpinner() {
        return "dist/component-spinner"; // component-spinner.html
    }

    @GetMapping("/component-toasts")
    public String componentToasts() {
        return "dist/component-toasts"; // component-toasts.html
    }

    @GetMapping("/component-tooltip")
    public String componentTooltip() {
        return "dist/component-tooltip"; // component-tooltip.html
    }

    @GetMapping("/customer")
    public String customer() {
        return "dist/customer"; // customer.html
    }

    @GetMapping("/error-403")
    public String error403() {
        return "dist/error-403"; // error-403.html
    }

    @GetMapping("/error-404")
    public String error404() {
        return "dist/error-404"; // error-404.html
    }

    @GetMapping("/error-500")
    public String error500() {
        return "dist/error-500"; // error-500.html
    }

    @GetMapping("/extra-component-avatar")
    public String extraComponentAvatar() {
        return "dist/extra-component-avatar"; // extra-component-avatar.html
    }

    @GetMapping("/extra-component-comment")
    public String extraComponentComment() {
        return "dist/extra-component-comment"; // extra-component-comment.html
    }

    @GetMapping("/extra-component-date-picker")
    public String extraComponentDatePicker() {
        return "dist/extra-component-date-picker"; // extra-component-date-picker.html
    }

    @GetMapping("/extra-component-divider")
    public String extraComponentDivider() {
        return "dist/extra-component-divider"; // extra-component-divider.html
    }

    @GetMapping("/extra-component-flag")
    public String extraComponentFlag() {
        return "dist/extra-component-flag"; // extra-component-flag.html
    }

    @GetMapping("/extra-component-rating")
    public String extraComponentRating() {
        return "dist/extra-component-rating"; // extra-component-rating.html
    }

    @GetMapping("/extra-component-sweetalert")
    public String extraComponentSweetalert() {
        return "dist/extra-component-sweetalert"; // extra-component-sweetalert.html
    }

    @GetMapping("/extra-component-toastify")
    public String extraComponentToastify() {
        return "dist/extra-component-toastify"; // extra-component-toastify.html
    }

    @GetMapping("/form-editor-ckeditor")
    public String formEditorCkeditor() {
        return "dist/form-editor-ckeditor"; // form-editor-ckeditor.html
    }

    @GetMapping("/form-editor-quill")
    public String formEditorQuill() {
        return "dist/form-editor-quill"; // form-editor-quill.html
    }

    @GetMapping("/form-editor-summernote")
    public String formEditorSummernote() {
        return "dist/form-editor-summernote"; // form-editor-summernote.html
    }

    @GetMapping("/form-editor-tinymce")
    public String formEditorTinymce() {
        return "dist/form-editor-tinymce"; // form-editor-tinymce.html
    }

    @GetMapping("/form-element-checkbox")
    public String formElementCheckbox() {
        return "dist/form-element-checkbox"; // form-element-checkbox.html
    }

    @GetMapping("/form-element-input-group")
    public String formElementInputGroup() {
        return "dist/form-element-input-group"; // form-element-input-group.html
    }

    @GetMapping("/form-element-input")
    public String formElementInput() {
        return "dist/form-element-input"; // form-element-input.html
    }

    @GetMapping("/form-element-radio")
    public String formElementRadio() {
        return "dist/form-element-radio"; // form-element-radio.html
    }

    @GetMapping("/form-element-select")
    public String formElementSelect() {
        return "dist/form-element-select"; // form-element-select.html
    }

    @GetMapping("/form-element-textarea")
    public String formElementTextarea() {
        return "dist/form-element-textarea"; // form-element-textarea.html
    }

    @GetMapping("/form-layout")
    public String formLayout() {
        return "dist/form-layout"; // form-layout.html
    }

    @GetMapping("/form-validation-parsley")
    public String formValidationParsley() {
        return "dist/form-validation-parsley"; // form-validation-parsley.html
    }

    @GetMapping("/index")
    public String index() {
        return "dist/index"; // index.html
    }

    @GetMapping("/layout-default")
    public String layoutDefault() {
        return "dist/layout-default"; // layout-default.html
    }

    @GetMapping("/layout-horizontal")
    public String layoutHorizontal() {
        return "dist/layout-horizontal"; // layout-horizontal.html
    }

    @GetMapping("/layout-rtl-backup")
    public String layoutRtlBackup() {
        return "dist/layout-rtl-backup"; // layout-rtl-backup.html
    }

    @GetMapping("/layout-rtl")
    public String layoutRtl() {
        return "dist/layout-rtl"; // layout-rtl.html
    }

    @GetMapping("/layout-vertical-1-column")
    public String layoutVertical1Column() {
        return "dist/layout-vertical-1-column"; // layout-vertical-1-column.html
    }

    @GetMapping("/layout-vertical-navbar")
    public String layoutVerticalNavbar() {
        return "dist/layout-vertical-navbar"; // layout-vertical-navbar.html
    }

    @GetMapping("/rawMaterialSupplier")
    public String rawMaterialSupplier() {
        return "dist/rawMaterialSupplier"; // raw_material_supplier.html
    }

    @GetMapping("/table-datatable-jquery")
    public String tableDatatableJquery() {
        return "dist/table-datatable-jquery"; // table-datatable-jquery.html
    }

    @GetMapping("/table-datatable")
    public String tableDatatable() {
        return "dist/table-datatable"; // table-datatable.html
    }

    @GetMapping("/ui-chart-apexcharts")
    public String uiChartApexcharts() {
        return "dist/ui-chart-apexcharts"; // ui-chart-apexcharts.html
    }

    @GetMapping("/ui-chart-chartjs")
    public String uiChartChartjs() {
        return "dist/ui-chart-chartjs"; // ui-chart-chartjs.html
    }

    @GetMapping("/ui-file-uploader")
    public String uiFileUploader() {
        return "dist/ui-file-uploader"; // ui-file-uploader.html
    }

    @GetMapping("/ui-icons-bootstrap-icons")
    public String uiIconsBootstrapIcons() {
        return "dist/ui-icons-bootstrap-icons"; // ui-icons-bootstrap-icons.html
    }

    @GetMapping("/ui-icons-dripicons")
    public String uiIconsDripicons() {
        return "dist/ui-icons-dripicons"; // ui-icons-dripicons.html
    }

    @GetMapping("/ui-icons-fontawesome")
    public String uiIconsFontawesome() {
        return "dist/ui-icons-fontawesome"; // ui-icons-fontawesome.html
    }

    @GetMapping("/ui-map-google-map")
    public String uiMapGoogleMap() {
        return "dist/ui-map-google-map"; // ui-map-google-map.html
    }

    @GetMapping("/ui-map-jsvectormap")
    public String uiMapJsvectormap() {
        return "dist/ui-map-jsvectormap"; // ui-map-jsvectormap.html
    }

    @GetMapping("/ui-multi-level-menu")
    public String uiMultiLevelMenu() {
        return "dist/ui-multi-level-menu"; // ui-multi-level-menu.html
    }

    @GetMapping("/ui-widgets-chatbox")
    public String uiWidgetsChatbox() {
        return "dist/ui-widgets-chatbox"; // ui-widgets-chatbox.html
    }

    @GetMapping("/ui-widgets-pricing")
    public String uiWidgetsPricing() {
        return "dist/ui-widgets-pricing"; // ui-widgets-pricing.html
    }

    @GetMapping("/ui-widgets-todolist")
    public String uiWidgetsTodolist() {
        return "dist/ui-widgets-todolist"; // ui-widgets-todolist.html
    }


}
