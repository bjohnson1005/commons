/*
*  Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/
package org.wso2.charon.core.objects;

import org.wso2.charon.core.attributes.Attribute;
import org.wso2.charon.core.attributes.ComplexAttribute;
import org.wso2.charon.core.attributes.DefaultAttributeFactory;
import org.wso2.charon.core.attributes.MultiValuedAttribute;
import org.wso2.charon.core.attributes.SimpleAttribute;
import org.wso2.charon.core.exceptions.CharonException;
import org.wso2.charon.core.exceptions.NotFoundException;
import org.wso2.charon.core.protocol.ResponseCodeConstants;
import org.wso2.charon.core.schema.AttributeSchema;
import org.wso2.charon.core.schema.SCIMConstants;
import org.wso2.charon.core.schema.SCIMSchemaDefinitions;
import org.wso2.charon.core.schema.SCIMSchemaDefinitions.DataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the User object which is a collection of attributes defined by SCIM User-schema.
 */
public class User extends AbstractSCIMObject {

    public User() {
        super();
    }

    /***********************UserName manipulation methods*************************************/
    /**
     * Set UserName attribute of the User.
     *
     * @param userName
     * @throws CharonException
     */
    public void setUserName(String userName) throws CharonException {
        setSimpleAttribute(SCIMConstants.UserSchemaConstants.USER_NAME, SCIMSchemaDefinitions.USER_NAME,
                           userName, DataType.STRING);
        /*if (isAttributeExist(SCIMConstants.UserSchemaConstants.USER_NAME)) {
            ((SimpleAttribute) attributeList.get(
                    SCIMConstants.UserSchemaConstants.USER_NAME)).updateValue(
                    userName, DataType.STRING);
        } else {
            //TODO:since the constructor is too long, pass an attribute schema.
            SimpleAttribute userNameAttribute = new SimpleAttribute(
                    SCIMConstants.UserSchemaConstants.USER_NAME, userName);
            *//*SimpleAttribute userNameAttribute = new SimpleAttribute(
                    SCIMConstants.UserSchemaConstants.USER_NAME,
                    SCIMConstants.CORE_SCHEMA_URI, userName, DataType.STRING,
                    false, false);*//*
            userNameAttribute = (SimpleAttribute) DefaultAttributeFactory.createAttribute(
                    SCIMSchemaDefinitions.USER_NAME, userNameAttribute);
            attributeList.put(SCIMConstants.UserSchemaConstants.USER_NAME, userNameAttribute);
        }*/
    }

    /**
     * Get UserName attribute of the user.
     *
     * @return
     * @throws NotFoundException
     * @throws CharonException
     */
    public String getUserName() throws CharonException {
        return getSimpleAttributeStringVal(SCIMConstants.UserSchemaConstants.DISPLAY_NAME);
        /*if (isAttributeExist(SCIMConstants.UserSchemaConstants.USER_NAME)) {
            return ((SimpleAttribute) attributeList.get(
                    SCIMConstants.UserSchemaConstants.USER_NAME)).getStringValue();

        } else {
            return null;
        }*/
    }

    /**
     * ******************************Email manipulation methods**************************
     */
    public void setEmail(Map<String, Object> propertyValues) throws CharonException {
        if (isAttributeExist(SCIMConstants.UserSchemaConstants.EMAILS)) {
            (attributeList.get(SCIMConstants.UserSchemaConstants.EMAILS)).
                    setComplexValue(propertyValues);
        } else {
            MultiValuedAttribute membersAttribute =
                    new MultiValuedAttribute(SCIMConstants.UserSchemaConstants.EMAILS);
            membersAttribute.setComplexValue(propertyValues);
            membersAttribute =
                    (MultiValuedAttribute) DefaultAttributeFactory.createAttribute(
                            SCIMSchemaDefinitions.EMAILS, membersAttribute);
            this.attributeList.put(SCIMConstants.UserSchemaConstants.EMAILS, membersAttribute);
        }
    }

    /**
     * Set the work email in the multi valued attribute - emails
     *
     * @param email
     * @param isPrimary
     * @throws CharonException
     */
    public void setWorkEmail(String email, boolean isPrimary) throws CharonException {
        /*MultiValuedAttribute emailsAttribute = new MultiValuedAttribute(
                SCIMConstants.UserSchemaConstants.EMAILS, SCIMConstants.CORE_SCHEMA_URI);
        emailsAttribute.setAttributeValue(SCIMConstants.UserSchemaConstants.WORK,
                                          isPrimary, null, email, DataType.STRING);
        attributeList.put(SCIMConstants.UserSchemaConstants.EMAILS, emailsAttribute);*/
        Map<String, Object> propertyValues = new HashMap<String, Object>();
        propertyValues.put(SCIMConstants.CommonSchemaConstants.VALUE, email);
        propertyValues.put(SCIMConstants.CommonSchemaConstants.TYPE, SCIMConstants.UserSchemaConstants.WORK);
        if (isPrimary) {
            propertyValues.put(SCIMConstants.CommonSchemaConstants.PRIMARY, isPrimary);
        }
        setEmail(propertyValues);
    }

    /**
     * Get the work email from the multi valued attribute - emails
     *
     * @return
     * @throws CharonException
     * @throws NotFoundException
     */
    public String getWorkEmail() throws CharonException, NotFoundException {
        MultiValuedAttribute emailsAttribute = (MultiValuedAttribute) attributeList.get(
                SCIMConstants.UserSchemaConstants.EMAILS);
        if (emailsAttribute != null) {
            return (String) emailsAttribute.getAttributeValueByType(
                    SCIMConstants.UserSchemaConstants.WORK);
        } else {
            return null;
        }
    }

    /**
     * Set home email in the multi valued attribute - emails.
     *
     * @param email
     * @param isPrimary
     * @throws CharonException
     */
    public void setHomeEmail(String email, boolean isPrimary) throws CharonException {
        /*MultiValuedAttribute emailsAttribute = new MultiValuedAttribute(
                SCIMConstants.UserSchemaConstants.EMAILS, SCIMConstants.CORE_SCHEMA_URI);
        emailsAttribute.setAttributeValue(SCIMConstants.UserSchemaConstants.HOME,
                                          isPrimary, null, email, DataType.STRING);
        attributeList.put(SCIMConstants.UserSchemaConstants.EMAILS, emailsAttribute);*/
        Map<String, Object> propertyValues = new HashMap<String, Object>();
        propertyValues.put(SCIMConstants.CommonSchemaConstants.VALUE, email);
        propertyValues.put(SCIMConstants.CommonSchemaConstants.TYPE, SCIMConstants.UserSchemaConstants.HOME);
        if (isPrimary) {
            propertyValues.put(SCIMConstants.CommonSchemaConstants.PRIMARY, isPrimary);
        }
        setEmail(propertyValues);
    }

    /**
     * Get the home email from the multi valued attribute - emails
     *
     * @return
     */
    public String getHomeEmail() throws CharonException, NotFoundException {
        MultiValuedAttribute emailsAttribute = (MultiValuedAttribute) attributeList.get(
                SCIMConstants.UserSchemaConstants.EMAILS);
        if (emailsAttribute != null) {
            return (String) emailsAttribute.getAttributeValueByType(
                    SCIMConstants.UserSchemaConstants.HOME);
        } else {
            return null;
        }
    }

    /**
     * Set any custom type email provided by user, in the multi valued attribute - email
     *
     * @param email
     * @param isPrimary
     * @param type
     * @throws CharonException
     */
    public void setOtherEmail(String email, boolean isPrimary, String type) throws CharonException {
        /*MultiValuedAttribute emailsAttribute = new MultiValuedAttribute(
                SCIMConstants.UserSchemaConstants.EMAILS, SCIMConstants.CORE_SCHEMA_URI);
        emailsAttribute.setAttributeValue(type,
                                          isPrimary, null, email, DataType.STRING);
        attributeList.put(SCIMConstants.UserSchemaConstants.EMAILS, emailsAttribute);*/
        Map<String, Object> propertyValues = new HashMap<String, Object>();
        propertyValues.put(SCIMConstants.CommonSchemaConstants.VALUE, email);
        propertyValues.put(SCIMConstants.CommonSchemaConstants.TYPE, type);
        if (isPrimary) {
            propertyValues.put(SCIMConstants.CommonSchemaConstants.PRIMARY, isPrimary);
        }
        setEmail(propertyValues);
    }


    public void setEmails(String[] emails) throws CharonException {
        /*MultiValuedAttribute emailsAttribute = new MultiValuedAttribute(
                SCIMConstants.UserSchemaConstants.EMAILS, SCIMConstants.CORE_SCHEMA_URI);
        for (String email : emails) {
            emailsAttribute.setSimpleAttributeValue(email, DataType.STRING);
        }
        attributeList.put(SCIMConstants.UserSchemaConstants.EMAILS, emailsAttribute);*/
        Map<String, Object> propertyValues = new HashMap<String, Object>();
        if (emails != null && emails.length != 0) {
            for (String email : emails) {
                propertyValues.put(SCIMConstants.CommonSchemaConstants.VALUE, email);
                setEmail(propertyValues);
            }
        }
    }

    /**
     * Get the email addresses as an array of Strings. Since emails is an multi-valued attribute
     * and since a multi-valued attribute can contain the values in different ways, needs to check
     * for all those possible ways.
     *
     * @return
     */
    public String[] getEmails() throws CharonException {

        if (isAttributeExist(SCIMConstants.UserSchemaConstants.EMAILS)) {
            //get the emails attribute
            MultiValuedAttribute emailsAttribute = (MultiValuedAttribute) attributeList.get(
                    SCIMConstants.UserSchemaConstants.EMAILS);
            //check if the values are stored just as a list of Strings.
            if (emailsAttribute.getValuesAsStrings() != null &&
                emailsAttribute.getValuesAsStrings().size() != 0) {

                return (String[]) emailsAttribute.getValuesAsStrings().toArray();
            } else {
                //check is the values are stored as simple of complex attributes
                List<Attribute> subAttributes = emailsAttribute.getValuesAsSubAttributes();
                List<String> values = new ArrayList<String>();
                if (subAttributes != null && subAttributes.size() != 0) {
                    for (Attribute subAttribute : subAttributes) {
                        //if value is a simple attribute of type: "value : "email";
                        if (subAttribute instanceof SimpleAttribute) {
                            values.add((String) ((SimpleAttribute) subAttribute).getValue());
                        } else if (subAttribute instanceof ComplexAttribute) {
                            //if the value is a complex attribute itself, obtain the "value" sub attribute and get the value
                            SimpleAttribute valueAttribute =
                                    (SimpleAttribute) (((ComplexAttribute) subAttribute).getSubAttribute(
                                            SCIMConstants.CommonSchemaConstants.VALUE));
                            values.add((String) valueAttribute.getValue());
                        }
                    }

                }
                String[] valuesAsStrings = null;
                if (values.size() != 0) {
                    valuesAsStrings = new String[values.size()];
                    int i = 0;
                    for (String value : values) {
                        valuesAsStrings[i] = value;
                        i++;
                    }
                }
                //return (String[])values.toString gave class cast exception
                return valuesAsStrings;
            }
        } else {
            return null;
        }
    }

    public String getPrimaryEmail() throws CharonException, NotFoundException {
        if (isAttributeExist(SCIMConstants.UserSchemaConstants.EMAILS)) {
            MultiValuedAttribute emailsAttribute = (MultiValuedAttribute) attributeList.get(
                    SCIMConstants.UserSchemaConstants.EMAILS);
            return (String) emailsAttribute.getPrimaryValue();
        } else {
            return null;
        }
    }

    /**
     * Retrieve any custom type email set by the user in the multi valued attribute - email
     *
     * @param type
     * @return
     * @throws CharonException
     * @throws NotFoundException
     */
    public String getEmailByType(String type) throws CharonException {
        if (isAttributeExist(SCIMConstants.UserSchemaConstants.EMAILS)) {
            MultiValuedAttribute emailsAttribute = (MultiValuedAttribute) attributeList.get(
                    SCIMConstants.UserSchemaConstants.EMAILS);
            return (String) emailsAttribute.getAttributeValueByType(type);
        } else {
            return null;
        }
    }

    /**
     * ************DisplayName manipulation methods.*************************************
     */

    public String getDisplayName() throws CharonException {
        return getSimpleAttributeStringVal(SCIMConstants.UserSchemaConstants.DISPLAY_NAME);
    }

    public void setDisplayName(String displayName) throws CharonException {
        setSimpleAttribute(SCIMConstants.UserSchemaConstants.DISPLAY_NAME,
                           SCIMSchemaDefinitions.USER_DISPLAY_NAME, displayName, DataType.STRING);
    }

    /**
     * ***********************Name manipulation methods**********************************
     */
    private void createName() throws CharonException {
        ComplexAttribute nameAttribute =
                (ComplexAttribute) DefaultAttributeFactory.createAttribute(
                        SCIMSchemaDefinitions.NAME, new ComplexAttribute(SCIMConstants.UserSchemaConstants.NAME));
        if (attributeList.containsKey(SCIMConstants.UserSchemaConstants.NAME)) {
            throw new CharonException(ResponseCodeConstants.ATTRIBUTE_ALREADY_EXIST);
        } else {
            attributeList.put(SCIMConstants.UserSchemaConstants.NAME, nameAttribute);
        }
    }

    private ComplexAttribute getNameAttribute() {
        if (attributeList.containsKey(SCIMConstants.UserSchemaConstants.NAME)) {
            return (ComplexAttribute) attributeList.get(SCIMConstants.UserSchemaConstants.NAME);
        } else {
            return null;
        }
    }

    private boolean isNameExist() {
        if (attributeList.containsKey(SCIMConstants.UserSchemaConstants.NAME)) {
            return true;
        } else {
            return false;
        }
    }
    //formatted name

    public void setFormattedName(String formattedName) throws CharonException {
        SimpleAttribute formattedAttribute =
                (SimpleAttribute) DefaultAttributeFactory.createAttribute(
                        SCIMSchemaDefinitions.FORMATTED,
                        new SimpleAttribute(SCIMConstants.UserSchemaConstants.FORMATTED_NAME, formattedName));
        if (isNameExist()) {
            ComplexAttribute nameAttribute = getNameAttribute();
            nameAttribute.setSubAttribute(formattedAttribute, SCIMSchemaDefinitions.FORMATTED);
        } else {
            createName();
            getNameAttribute().setSubAttribute(formattedAttribute, SCIMSchemaDefinitions.FORMATTED);
        }
    }

    public String getFormattedName() throws CharonException {
        if (getNameAttribute() != null) {
            ComplexAttribute nameAttribute = getNameAttribute();
            SimpleAttribute formattedName = (SimpleAttribute) nameAttribute.getSubAttribute(
                    SCIMConstants.UserSchemaConstants.FORMATTED_NAME);
            if (formattedName != null) {
                return formattedName.getStringValue();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    //family name

    public void setFamilyName(String familyName) throws CharonException {
        SimpleAttribute familyNameAttribute =
                (SimpleAttribute) DefaultAttributeFactory.createAttribute(
                        SCIMSchemaDefinitions.FAMILY_NAME,
                        new SimpleAttribute(SCIMConstants.UserSchemaConstants.FAMILY_NAME, familyName));
        if (isNameExist()) {
            ComplexAttribute nameAttribute = getNameAttribute();
            nameAttribute.setSubAttribute(familyNameAttribute, SCIMSchemaDefinitions.FAMILY_NAME);
        } else {
            createName();
            getNameAttribute().setSubAttribute(familyNameAttribute, SCIMSchemaDefinitions.FAMILY_NAME);
        }
    }

    public String getFamilyName() throws CharonException {
        if (getNameAttribute() != null) {
            ComplexAttribute nameAttribute = getNameAttribute();
            SimpleAttribute familyName = (SimpleAttribute) nameAttribute.getSubAttribute(
                    SCIMConstants.UserSchemaConstants.FAMILY_NAME);
            if (familyName != null) {
                return familyName.getStringValue();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    //given name

    public void setGivenName(String givenName) throws CharonException {
        SimpleAttribute givenNameAttribute =
                (SimpleAttribute) DefaultAttributeFactory.createAttribute(
                        SCIMSchemaDefinitions.GIVEN_NAME,
                        new SimpleAttribute(SCIMConstants.UserSchemaConstants.GIVEN_NAME, givenName));
        if (isNameExist()) {
            ComplexAttribute nameAttribute = getNameAttribute();
            nameAttribute.setSubAttribute(givenNameAttribute, SCIMSchemaDefinitions.GIVEN_NAME);
        } else {
            createName();
            getNameAttribute().setSubAttribute(givenNameAttribute, SCIMSchemaDefinitions.GIVEN_NAME);
        }
    }

    public String getGivenName() throws CharonException {
        if (getNameAttribute() != null) {
            ComplexAttribute nameAttribute = getNameAttribute();
            SimpleAttribute givenName = (SimpleAttribute) nameAttribute.getSubAttribute(
                    SCIMConstants.UserSchemaConstants.GIVEN_NAME);
            if (givenName != null) {
                return givenName.getStringValue();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    //middle name

    public void setMiddleName(String middleName) throws CharonException {
        SimpleAttribute middleNameAttribute =
                (SimpleAttribute) DefaultAttributeFactory.createAttribute(
                        SCIMSchemaDefinitions.MIDDLE_NAME,
                        new SimpleAttribute(SCIMConstants.UserSchemaConstants.MIDDLE_NAME, middleName));
        if (isNameExist()) {
            ComplexAttribute nameAttribute = getNameAttribute();
            nameAttribute.setSubAttribute(middleNameAttribute, SCIMSchemaDefinitions.MIDDLE_NAME);
        } else {
            createName();
            getNameAttribute().setSubAttribute(middleNameAttribute, SCIMSchemaDefinitions.MIDDLE_NAME);
        }
    }

    public String getMiddleName() throws CharonException {
        if (getNameAttribute() != null) {
            ComplexAttribute nameAttribute = getNameAttribute();
            SimpleAttribute middleName = (SimpleAttribute) nameAttribute.getSubAttribute(
                    SCIMConstants.UserSchemaConstants.MIDDLE_NAME);
            if (middleName != null) {
                return middleName.getStringValue();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    //honorificPrefix

    public void setHonorificPrefix(String honorificPrefix) throws CharonException {
        SimpleAttribute honorificPrefixAttribute =
                (SimpleAttribute) DefaultAttributeFactory.createAttribute(
                        SCIMSchemaDefinitions.HONORIFIC_PREFIX,
                        new SimpleAttribute(SCIMConstants.UserSchemaConstants.HONORIFIC_PREFIX, honorificPrefix));
        if (isNameExist()) {
            ComplexAttribute nameAttribute = getNameAttribute();
            nameAttribute.setSubAttribute(honorificPrefixAttribute, SCIMSchemaDefinitions.HONORIFIC_PREFIX);
        } else {
            createName();
            getNameAttribute().setSubAttribute(honorificPrefixAttribute, SCIMSchemaDefinitions.HONORIFIC_PREFIX);
        }
    }

    public String getHonorificPrefix() throws CharonException {
        if (getNameAttribute() != null) {
            ComplexAttribute nameAttribute = getNameAttribute();
            SimpleAttribute middleName = (SimpleAttribute) nameAttribute.getSubAttribute(
                    SCIMConstants.UserSchemaConstants.HONORIFIC_PREFIX);
            if (middleName != null) {
                return middleName.getStringValue();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    //honorificSuffix

    public void setHonorificSuffix(String honorificSuffix) throws CharonException {
        SimpleAttribute honorificSuffixAttribute =
                (SimpleAttribute) DefaultAttributeFactory.createAttribute(
                        SCIMSchemaDefinitions.HONORIFIC_SUFFIX,
                        new SimpleAttribute(SCIMConstants.UserSchemaConstants.HONORIFIC_SUFFIX, honorificSuffix));
        if (isNameExist()) {
            ComplexAttribute nameAttribute = getNameAttribute();
            nameAttribute.setSubAttribute(honorificSuffixAttribute, SCIMSchemaDefinitions.HONORIFIC_SUFFIX);
        } else {
            createName();
            getNameAttribute().setSubAttribute(honorificSuffixAttribute, SCIMSchemaDefinitions.HONORIFIC_SUFFIX);
        }
    }

    public String getHonorificSuffix() throws CharonException {
        if (getNameAttribute() != null) {
            ComplexAttribute nameAttribute = getNameAttribute();
            SimpleAttribute honorificSuffixAttribute = (SimpleAttribute) nameAttribute.getSubAttribute(
                    SCIMConstants.UserSchemaConstants.HONORIFIC_SUFFIX);
            if (honorificSuffixAttribute != null) {
                return honorificSuffixAttribute.getStringValue();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * *********************Nick Name*************************************************
     */
    public void setNickName(String nickName) throws CharonException {
        setSimpleAttribute(SCIMConstants.UserSchemaConstants.NICK_NAME,
                           SCIMSchemaDefinitions.NICK_NAME, nickName, DataType.STRING);
    }

    public String getNickName() throws CharonException {
        return getSimpleAttributeStringVal(SCIMConstants.UserSchemaConstants.NICK_NAME);
    }

    /**
     * *********************Profile URL***********************************************
     */
    public void setProfileURL(String profileURL) throws CharonException {
        setSimpleAttribute(SCIMConstants.UserSchemaConstants.PROFILE_URL,
                           SCIMSchemaDefinitions.PROFILE_URL, profileURL, DataType.STRING);
    }

    public String getProfileURL() throws CharonException {
        return getSimpleAttributeStringVal(SCIMConstants.UserSchemaConstants.PROFILE_URL);
    }

    /**
     * ***************************Title*******************************************
     */
    public void setTitle(String title) throws CharonException {
        setSimpleAttribute(SCIMConstants.UserSchemaConstants.TITLE, SCIMSchemaDefinitions.TITLE,
                           title, DataType.STRING);
    }

    public String getTitle() throws CharonException {
        return getSimpleAttributeStringVal(SCIMConstants.UserSchemaConstants.TITLE);
    }

    /**
     * ***************************User Type***************************************
     */
    public void setUserType(String userType) throws CharonException {
        setSimpleAttribute(SCIMConstants.UserSchemaConstants.USER_TYPE,
                           SCIMSchemaDefinitions.USER_TYPE, userType, DataType.STRING);
    }

    public String getUserType() throws CharonException {
        return getSimpleAttributeStringVal(SCIMConstants.UserSchemaConstants.USER_TYPE);
    }

    /**
     * ***************************Preferred Language******************************
     */
    public void setPreferredLanguage(String preferredLanguage) throws CharonException {
        setSimpleAttribute(SCIMConstants.UserSchemaConstants.PREFERRED_LANGUAGE,
                           SCIMSchemaDefinitions.PREFERRED_LANGUAGE, preferredLanguage, DataType.STRING);
    }

    public String getPreferredLanguage() throws CharonException {
        return getSimpleAttributeStringVal(SCIMConstants.UserSchemaConstants.PREFERRED_LANGUAGE);
    }

    /**
     * ***************************Locale******************************************
     */
    public void setLocale(String locale) throws CharonException {
        setSimpleAttribute(SCIMConstants.UserSchemaConstants.LOCALE, SCIMSchemaDefinitions.LOCALE,
                           locale, DataType.STRING);
    }

    public String getLocale() throws CharonException {
        return getSimpleAttributeStringVal(SCIMConstants.UserSchemaConstants.LOCALE);
    }

    /**
     * *******************************Time Zone***************************************
     */
    public void setTimeZone(String timeZone) throws CharonException {
        setSimpleAttribute(SCIMConstants.UserSchemaConstants.TIME_ZONE,
                           SCIMSchemaDefinitions.TIMEZONE, timeZone, DataType.STRING);
    }

    public String getTimeZone() throws CharonException {
        return getSimpleAttributeStringVal(SCIMConstants.UserSchemaConstants.TIME_ZONE);
    }

    /**
     * ********************************Active****************************************
     */
    public void setActive(Boolean active) throws CharonException {
        setSimpleAttribute(SCIMConstants.UserSchemaConstants.ACTIVE,
                           SCIMSchemaDefinitions.ACTIVE, active, DataType.BOOLEAN);
    }

    public Boolean getActive() throws CharonException {
        if (isAttributeExist(SCIMConstants.UserSchemaConstants.ACTIVE)) {
            return ((SimpleAttribute) attributeList.get(SCIMConstants.UserSchemaConstants.ACTIVE)).getBooleanValue();
        } else {
            return null;
        }
    }

    /**
     * ********************************Password**************************************
     */
    public void setPassword(String password) throws CharonException {
        setSimpleAttribute(SCIMConstants.UserSchemaConstants.PASSWORD, SCIMSchemaDefinitions.PASSWORD,
                           password, DataType.STRING);
    }

    public String getPassword() throws CharonException {
        return getSimpleAttributeStringVal(SCIMConstants.UserSchemaConstants.PASSWORD);
    }

    /**
     * ********************************Phone Numbers**********************************
     */
    public void setPhoneNumber(String phoneNumber, String type, boolean isPrimary)
            throws CharonException {
        Map<String, Object> phoneNumberProperties = new HashMap<String, Object>();
        phoneNumberProperties.put(SCIMConstants.CommonSchemaConstants.VALUE, phoneNumber);
        phoneNumberProperties.put(SCIMConstants.CommonSchemaConstants.TYPE, type);
        if (isPrimary) {
            phoneNumberProperties.put(SCIMConstants.CommonSchemaConstants.PRIMARY, isPrimary);
        }
        if (isAttributeExist(SCIMConstants.UserSchemaConstants.PHONE_NUMBERS)) {
            attributeList.get(SCIMConstants.UserSchemaConstants.PHONE_NUMBERS).setComplexValue(
                    phoneNumberProperties);
        } else {
            MultiValuedAttribute multiValuedAttribute =
                    new MultiValuedAttribute(SCIMConstants.UserSchemaConstants.PHONE_NUMBERS);
            multiValuedAttribute.setComplexValue(phoneNumberProperties);
            multiValuedAttribute = (MultiValuedAttribute) DefaultAttributeFactory.createAttribute(
                    SCIMSchemaDefinitions.PHONE_NUMBERS, multiValuedAttribute);
            attributeList.put(SCIMConstants.UserSchemaConstants.PHONE_NUMBERS, multiValuedAttribute);
        }
    }

    public String getPhoneNumber(String type) throws CharonException {
        if (isAttributeExist(SCIMConstants.UserSchemaConstants.PHONE_NUMBERS)) {
            return (String) ((MultiValuedAttribute) attributeList.get(
                    SCIMConstants.UserSchemaConstants.PHONE_NUMBERS)).getAttributeValueByType(type);

        } else {
            return null;
        }
    }

    public List<String> getPhoneNumbers(String type) throws CharonException {
        if (isAttributeExist(SCIMConstants.UserSchemaConstants.PHONE_NUMBERS)) {
            return ((MultiValuedAttribute) attributeList.get(
                    SCIMConstants.UserSchemaConstants.PHONE_NUMBERS)).getAttributeValuesByType(type);

        } else {
            return null;
        }
    }

    public String getPrimaryPhoneNumber() throws CharonException {
        if (isAttributeExist(SCIMConstants.UserSchemaConstants.PHONE_NUMBERS)) {
            return (String) ((MultiValuedAttribute) attributeList.get(
                    SCIMConstants.UserSchemaConstants.PHONE_NUMBERS)).getPrimaryValue();

        } else {
            return null;
        }
    }

    /**
     * *******************************IMs*******************************************
     */
    public void setIM(String imAddress, String type, Boolean isPrimary) throws CharonException {
        Map<String, Object> imProperties = new HashMap<String, Object>();
        imProperties.put(SCIMConstants.CommonSchemaConstants.VALUE, imAddress);
        imProperties.put(SCIMConstants.CommonSchemaConstants.TYPE, type);
        if (isPrimary) {
            imProperties.put(SCIMConstants.CommonSchemaConstants.PRIMARY, isPrimary);
        }
        if (isAttributeExist(SCIMConstants.UserSchemaConstants.IMS)) {
            attributeList.get(SCIMConstants.UserSchemaConstants.IMS).setComplexValue(
                    imProperties);
        } else {
            MultiValuedAttribute multiValuedAttribute =
                    new MultiValuedAttribute(SCIMConstants.UserSchemaConstants.IMS);
            multiValuedAttribute.setComplexValue(imProperties);
            multiValuedAttribute = (MultiValuedAttribute) DefaultAttributeFactory.createAttribute(
                    SCIMSchemaDefinitions.IMS, multiValuedAttribute);
            attributeList.put(SCIMConstants.UserSchemaConstants.IMS, multiValuedAttribute);
        }
    }

    public String getIM(String type) throws CharonException {
        if (isAttributeExist(SCIMConstants.UserSchemaConstants.IMS)) {
            return (String) ((MultiValuedAttribute) attributeList.get(
                    SCIMConstants.UserSchemaConstants.IMS)).getAttributeValueByType(type);

        } else {
            return null;
        }
    }

    public List<String> getIMs(String im) throws CharonException {
        if (isAttributeExist(SCIMConstants.UserSchemaConstants.IMS)) {
            return ((MultiValuedAttribute) attributeList.get(
                    SCIMConstants.UserSchemaConstants.IMS)).getAttributeValuesByType(im);

        } else {
            return null;
        }
    }

    public String getPrimaryIM() throws CharonException {
        if (isAttributeExist(SCIMConstants.UserSchemaConstants.IMS)) {
            return (String) ((MultiValuedAttribute) attributeList.get(
                    SCIMConstants.UserSchemaConstants.IMS)).getPrimaryValue();
        } else {
            return null;
        }
    }

    /**
     * *************************Groups Attribute***************************************
     */
    //types: direct, indirect


    /**
     * Update the attribute value by attribute name. Needs to be overloaded by specific types of
     * attributes.
     *
     * @param attributeName
     * @param attributeValue public void updateValue(Object value) {
     *                       this.value = value;
     *                       }
     *                       public void updateValue(Object value) {
     *                       this.value = value;
     *                       }
     */
    /*@Override
    public void updateAttribute(String attributeName, Object attributeValue) {
        //To change body of implemented methods use File | Settings | File Templates.
    }*/

    /**
     * Validates whether the given SCIM object adheres to the SCIM schema.
     *
     * @param scimObject
     * @return
     */
    public boolean validate(SCIMObject scimObject) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Take common functionality of setting a value to a simple attribute, into one place.
     *
     * @param attributeName
     * @param attributeSchema
     * @param value
     * @param dataType
     * @throws CharonException
     */
    private void setSimpleAttribute(String attributeName, AttributeSchema attributeSchema,
                                    Object value, DataType dataType) throws CharonException {
        if (isAttributeExist(attributeName)) {
            //since we check read-only aspect in service provider side, no need to check it here.
            //if (!attributeSchema.getReadOnly()) {
            ((SimpleAttribute) attributeList.get(attributeName)).updateValue(value, dataType);
            /*} else {
                //log info level log that version already set and can't set again.
                throw new CharonException(ResponseCodeConstants.ATTRIBUTE_READ_ONLY);
            }*/
        } else {
            SimpleAttribute simpleAttribute = new SimpleAttribute(
                    attributeName, value);
            /*SimpleAttribute userNameAttribute = new SimpleAttribute(
                    SCIMConstants.UserSchemaConstants.USER_NAME,
                    SCIMConstants.CORE_SCHEMA_URI, userName, DataType.STRING,
                    false, false);*/
            simpleAttribute = (SimpleAttribute) DefaultAttributeFactory.createAttribute(
                    attributeSchema, simpleAttribute);
            attributeList.put(attributeName, simpleAttribute);
        }
    }

    private String getSimpleAttributeStringVal(String attributeName) throws CharonException {
        if (isAttributeExist(attributeName)) {
            return ((SimpleAttribute) attributeList.get(attributeName)).getStringValue();
        } else {
            return null;
        }
    }


}
