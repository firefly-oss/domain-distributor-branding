package com.firefly.domain.distributor.branding.core.utils.constants;

public class DistributorConstants {

    // ============================== SAGA CONFIGURATION ==============================
    public static final String SAGA_REGISTER_DISTRIBUTOR = "RegisterDistributorSaga";
    public static final String SAGA_UPDATE_BRANDING = "UpdateBrandingSaga";
    public static final String SAGA_SET_DEFAULT_BRANDING = "SetDefaultBrandingSaga";
    public static final String SAGA_UPDATE_TERMS_AND_CONDITIONS = "UpdateTermsAndConditionsSaga";


    // ============================== STEP IDENTIFIERS ==============================
    public static final String STEP_REGISTER_DISTRIBUTOR = "registerDistributor";
    public static final String STEP_REGISTER_TANDC_TEMPLATE = "registerTAndCTemplate";
    public static final String STEP_REGISTER_TERMS_AND_CONDITIONS = "registerTermsAndConditions";
    public static final String STEP_REGISTER_AUDIT_LOG = "registerAuditLog";
    public static final String STEP_REGISTER_BRANDING = "registerBranding";
    public static final String STEP_UPDATE_BRANDING = "updateBranding";
    public static final String STEP_SET_DEFAULT_BRANDING = "setDefaultBranding";
    public static final String STEP_UPDATE_TERMS_AND_CONDITIONS = "updateTermsAndConditions";


    // ============================== COMPENSATE METHODS ==============================
    public static final String COMPENSATE_REMOVE_DISTRIBUTOR = "removeDistributor";
    public static final String COMPENSATE_REMOVE_TANDC_TEMPLATE = "removeTAndCTemplate";
    public static final String COMPENSATE_REMOVE_TERMS_AND_CONDITIONS = "removeTermsAndConditions";
    public static final String COMPENSATE_REMOVE_AUDIT_LOG = "removeAuditLog";
    public static final String COMPENSATE_REMOVE_BRANDING = "removeBranding";

    // ============================== EVENT TYPES ==============================
    public static final String EVENT_DISTRIBUTOR_REGISTERED = "distributor.registered";
    public static final String EVENT_TANDC_TEMPLATE_REGISTERED = "tAndCTemplate.registered";
    public static final String EVENT_TERMS_AND_CONDITIONS_REGISTERED = "termsAndConditions.registered";
    public static final String EVENT_AUDIT_LOG_REGISTERED = "auditLog.registered";
    public static final String EVENT_BRANDING_REGISTERED = "branding.registered";
    public static final String EVENT_BRANDING_UPDATED = "branding.updated";
    public static final String EVENT_TERMS_AND_CONDITIONS_UPDATED = "termsAndConditions.updated";


}
