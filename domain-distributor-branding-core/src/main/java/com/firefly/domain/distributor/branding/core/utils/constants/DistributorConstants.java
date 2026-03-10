package com.firefly.domain.distributor.branding.core.utils.constants;

public class DistributorConstants {

    // ============================== SAGA CONFIGURATION ==============================
    public static final String SAGA_REGISTER_DISTRIBUTOR = "RegisterDistributorSaga";
    public static final String SAGA_UPDATE_BRANDING = "UpdateBrandingSaga";
    public static final String SAGA_SET_DEFAULT_BRANDING = "SetDefaultBrandingSaga";
    public static final String SAGA_UPDATE_TERMS_AND_CONDITIONS = "UpdateTermsAndConditionsSaga";
    public static final String SAGA_REGISTER_AGENCY = "RegisterAgencySaga";
    public static final String SAGA_REGISTER_AGENT = "RegisterAgentSaga";


    // ============================== STEP IDENTIFIERS ==============================
    public static final String STEP_REGISTER_DISTRIBUTOR = "registerDistributor";
    public static final String STEP_REGISTER_TANDC_TEMPLATE = "registerTAndCTemplate";
    public static final String STEP_REGISTER_TERMS_AND_CONDITIONS = "registerTermsAndConditions";
    public static final String STEP_REGISTER_AUDIT_LOG = "registerAuditLog";
    public static final String STEP_REGISTER_BRANDING = "registerBranding";
    public static final String STEP_UPDATE_BRANDING = "updateBranding";
    public static final String STEP_SET_DEFAULT_BRANDING = "setDefaultBranding";
    public static final String STEP_UPDATE_TERMS_AND_CONDITIONS = "updateTermsAndConditions";

    // RegisterAgencySaga steps
    public static final String STEP_CREATE_TERRITORY = "createTerritory";
    public static final String STEP_CREATE_AGENCY = "createAgency";
    public static final String STEP_CREATE_OPERATION = "createOperation";

    // RegisterAgentSaga steps
    public static final String STEP_CREATE_AGENT = "createAgent";
    public static final String STEP_ASSIGN_AGENT_AGENCY = "assignAgentAgency";


    // ============================== COMPENSATE METHODS ==============================
    public static final String COMPENSATE_REMOVE_DISTRIBUTOR = "removeDistributor";
    public static final String COMPENSATE_REMOVE_TANDC_TEMPLATE = "removeTAndCTemplate";
    public static final String COMPENSATE_REMOVE_TERMS_AND_CONDITIONS = "removeTermsAndConditions";
    public static final String COMPENSATE_REMOVE_AUDIT_LOG = "removeAuditLog";
    public static final String COMPENSATE_REMOVE_BRANDING = "removeBranding";

    // RegisterAgencySaga compensation
    public static final String COMPENSATE_DELETE_TERRITORY = "deleteTerritory";
    public static final String COMPENSATE_DELETE_AGENCY = "deleteAgency";
    public static final String COMPENSATE_DELETE_OPERATION = "deleteOperation";

    // RegisterAgentSaga compensation
    public static final String COMPENSATE_DELETE_AGENT = "deleteAgent";
    public static final String COMPENSATE_REMOVE_AGENT_AGENCY = "removeAgentAgency";

    // ============================== EVENT TYPES ==============================
    public static final String EVENT_DISTRIBUTOR_REGISTERED = "distributor.registered";
    public static final String EVENT_TANDC_TEMPLATE_REGISTERED = "tAndCTemplate.registered";
    public static final String EVENT_TERMS_AND_CONDITIONS_REGISTERED = "termsAndConditions.registered";
    public static final String EVENT_AUDIT_LOG_REGISTERED = "auditLog.registered";
    public static final String EVENT_BRANDING_REGISTERED = "branding.registered";
    public static final String EVENT_BRANDING_UPDATED = "branding.updated";
    public static final String EVENT_TERMS_AND_CONDITIONS_UPDATED = "termsAndConditions.updated";

    // RegisterAgencySaga events
    public static final String EVENT_TERRITORY_CREATED = "territory.created";
    public static final String EVENT_AGENCY_CREATED = "agency.created";
    public static final String EVENT_OPERATION_CREATED = "operation.created";

    // RegisterAgentSaga events
    public static final String EVENT_AGENT_CREATED = "agent.created";
    public static final String EVENT_AGENT_AGENCY_ASSIGNED = "agentAgency.assigned";


}
