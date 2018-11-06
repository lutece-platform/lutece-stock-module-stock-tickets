/*
 * Copyright (c) 2002-2018, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.stock.modules.tickets.business;

import fr.paris.lutece.plugins.stock.business.attribute.provider.ProviderAttribute;
import fr.paris.lutece.plugins.stock.business.attribute.provider.ProviderAttributeNum;
import fr.paris.lutece.plugins.stock.business.provider.Provider;
import fr.paris.lutece.plugins.stock.commons.AbstractDTO;
import fr.paris.lutece.plugins.stock.commons.ResultList;
import fr.paris.lutece.portal.service.spring.SpringContextService;

import org.apache.commons.lang.StringUtils;

import org.dozer.Mapper;

import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

/**
 * DTO for provider
 *
 * @author abataille
 */
public class PartnerDTO extends AbstractDTO<Provider>
{
    // CONSTANTS
    public static final String STRING_TRUE = "true";
    public static final String STRING_FALSE = "false";
    public static final String ATTR_CONTACT_NAME = "contactName";
    public static final String ATTR_CONTACT_ID = "contactId";
    public static final String ATTR_CONTACT_PHONE = "phoneNumber";
    public static final String ATTR_CONTACT_MAIL = "mail";
    public static final String ATTR_IS_ACCESSIBLE = "accessible";
    public static final String ATTR_ACCESSIBLE_COMMENT = "accessibleComment";
    public static final String ATTR_METRO_COMMENT = "metroComment";
    public static final String ATTR_DISTRICT = "district";
    private Integer id;
    @NotEmpty
    private String name;
    private String address;
    private boolean accessible;
    private String accessibleComment;
    private String metroComment;
    private String comment;
    private Integer district;

    /**
     * List of the contact of this partner
     */
    private List<Contact> contactList = new ArrayList<Contact>( );

    /**
     * Methode use to find a contact with it's id
     * 
     * @param id
     *            the contact id
     * @return the contact or null if he doesn't exist
     */
    private Contact findContactById( int id )
    {
        boolean trouve = false;
        int i = 0;
        Contact res = null;

        while ( !trouve && ( i < contactList.size( ) ) )
        {
            if ( contactList.get( i ).getId( ) == id )
            {
                res = contactList.get( i );
                trouve = true;
            }

            i++;
        }

        return res;
    }

    /**
     * Add an empty contact to the partner
     */
    public void addEmptyContact( )
    {
        int i = 0;

        for ( Contact c : contactList )
        {
            if ( c.getId( ) >= i )
            {
                i = c.getId( ) + 1;
            }
        }

        this.contactList.add( new Contact( i, "", "", "" ) );
    }

    /**
     * @return the idProvider
     */
    public Integer getId( )
    {
        return id;
    }

    /**
     * @param idProvider
     *            the idProvider to set
     */
    public void setId( Integer idProvider )
    {
        this.id = idProvider;
    }

    /**
     * @return the name
     */
    public String getName( )
    {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName( String name )
    {
        this.name = name;
    }

    /**
     * @return the address
     */
    public String getAddress( )
    {
        return address;
    }

    /**
     * @param address
     *            the address to set
     */
    public void setAddress( String address )
    {
        this.address = address;
    }

    /**
     * @return the contactName
     */
    public String getContactName( int idContact )
    {
        return contactList.get( idContact ).getName( );
    }

    /**
     * @param contactName
     *            the contactName to set
     */
    public void setContactName( int idContact, String contactName )
    {
        if ( findContactById( idContact ) == null )
        {
            contactList.add( new Contact( idContact, "", "", "" ) );
        }

        findContactById( idContact ).setName( contactName );
    }

    /**
     * @return the phoneNumber
     */
    public String getContactPhoneNumber( int idContact )
    {
        return findContactById( idContact ).getPhoneNumber( );
    }

    /**
     * @param phoneNumber
     *            the phoneNumber to set
     */
    public void setContactPhoneNumber( int idContact, String phoneNumber )
    {
        if ( findContactById( idContact ) == null )
        {
            contactList.add( new Contact( idContact, "", "", "" ) );
        }

        findContactById( idContact ).setPhoneNumber( phoneNumber );
    }

    /**
     * @return the mail
     */
    public String getContactMail( int idContact )
    {
        String mail = null;
        Contact contact = findContactById( idContact );

        if ( contact != null )
        {
            mail = contact.getMail( );
        }

        return mail;
    }

    /**
     * @param mail
     *            the mail to set
     */
    public void setContactMail( int idContact, String mail )
    {
        if ( findContactById( idContact ) == null )
        {
            contactList.add( new Contact( idContact, "", "", "" ) );
        }

        findContactById( idContact ).setMail( mail );
    }

    /**
     * @return the id
     */
    public int getContactId( int idContact )
    {
        return findContactById( idContact ).getId( );
    }

    /**
     * @param id
     *            the id to set
     */
    public void setContactId( int idContact, int id )
    {
        if ( findContactById( idContact ) == null )
        {
            contactList.add( new Contact( idContact, "", "", "" ) );
        }

        findContactById( idContact ).setId( id );
    }

    /**
     * Gets the comment.
     *
     * @return the comment
     */
    public String getComment( )
    {
        return comment;
    }

    /**
     * Sets the comment.
     *
     * @param comment
     *            the comment to set
     */
    public void setComment( String comment )
    {
        this.comment = comment;
    }

    /**
     * @return boolean is Accessible
     */
    public boolean isAccessible( )
    {
        return accessible;
    }

    /**
     * @param accessible
     *            true or false
     */
    public void setAccessible( boolean accessible )
    {
        this.accessible = accessible;
    }

    /**
     * @return accessibleComment
     */
    public String getAccessibleComment( )
    {
        return accessibleComment;
    }

    /**
     * @param accessibleComment
     *            the new comment for accessibility
     */
    public void setAccessibleComment( String accessibleComment )
    {
        this.accessibleComment = accessibleComment;
    }

    /**
     * @return metro comment
     */
    public String getMetroComment( )
    {
        return metroComment;
    }

    /**
     * @param metroComment
     */
    public void setMetroComment( String metroComment )
    {
        this.metroComment = metroComment;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Provider convert( )
    {
        Provider provider = mapper.map( this, Provider.class );

        // Accessibility
        if ( this.isAccessible( ) )
        {
            provider.getAttributeList( ).add( new ProviderAttribute( ATTR_IS_ACCESSIBLE, STRING_TRUE, provider ) );
        }
        else
        {
            provider.getAttributeList( ).add( new ProviderAttribute( ATTR_IS_ACCESSIBLE, STRING_FALSE, provider ) );
        }

        if ( ( district != null ) && ( district > 0 ) )
        {
            provider.getAttributeNumList( ).add( new ProviderAttributeNum( ATTR_DISTRICT, new BigDecimal( district ), provider ) );
        }

        if ( StringUtils.isNotEmpty( this.getAccessibleComment( ) ) )
        {
            provider.getAttributeList( ).add( new ProviderAttribute( ATTR_ACCESSIBLE_COMMENT, this.getAccessibleComment( ), provider ) );
        }

        // Metro comment
        if ( StringUtils.isNotEmpty( this.getMetroComment( ) ) )
        {
            provider.getAttributeList( ).add( new ProviderAttribute( ATTR_METRO_COMMENT, this.getMetroComment( ), provider ) );
        }

        for ( Contact contact : contactList )
        {
            provider.getAttributeList( ).add(
                    new ProviderAttribute( "contactId" + String.valueOf( contact.getId( ) ), String.valueOf( contact.getId( ) ), provider ) );
            provider.getAttributeList( ).add( new ProviderAttribute( "contactName" + contact.getId( ), contact.getName( ), provider ) );
            provider.getAttributeList( ).add( new ProviderAttribute( "phoneNumber" + contact.getId( ), contact.getPhoneNumber( ), provider ) );
            provider.getAttributeList( ).add( new ProviderAttribute( "mail" + contact.getId( ), contact.getMail( ), provider ) );
        }

        return provider;
    }

    /**
     * Return DTO converted from entity bean
     * 
     * @param source
     *            entity bean
     * @return dto
     */
    public static PartnerDTO convertEntity( Provider source )
    {
        Mapper mapper = (Mapper) SpringContextService.getBean( "mapper" );
        PartnerDTO partnerDTO = mapper.map( source, PartnerDTO.class );

        Set<ProviderAttributeNum> attributeNumList = source.getAttributeNumList( );

        if ( attributeNumList != null )
        {
            for ( ProviderAttributeNum attribute : attributeNumList )
            {
                if ( ATTR_DISTRICT.equals( attribute.getKey( ) ) )
                {
                    partnerDTO.setDistrict( attribute.getValue( ).intValue( ) );
                }
            }
        }

        Set<ProviderAttribute> attributeList = source.getAttributeList( );

        if ( attributeList != null )
        {
            for ( ProviderAttribute attribute : attributeList )
            {
                // Accessibility
                if ( ATTR_IS_ACCESSIBLE.equals( attribute.getKey( ) ) )
                {
                    if ( attribute.getValue( ).equals( STRING_TRUE ) )
                    {
                        partnerDTO.setAccessible( true );
                    }
                    else
                    {
                        partnerDTO.setAccessible( false );
                    }
                }

                else
                    if ( ATTR_ACCESSIBLE_COMMENT.equals( attribute.getKey( ) ) )
                    {
                        partnerDTO.setAccessibleComment( attribute.getValue( ) );
                    }

                    // Metro comment
                    else
                        if ( ATTR_METRO_COMMENT.equals( attribute.getKey( ) ) )
                        {
                            partnerDTO.setMetroComment( attribute.getValue( ) );
                        }

                        // Contacts
                        else
                            if ( attribute.getKey( ).startsWith( ATTR_CONTACT_NAME ) )
                            {
                                int id = Integer.valueOf( attribute.getKey( ).substring( ATTR_CONTACT_NAME.length( ) ) );
                                partnerDTO.setContactName( id, attribute.getValue( ) );
                            }
                            else
                                if ( attribute.getKey( ).startsWith( ATTR_CONTACT_PHONE ) )
                                {
                                    int id = Integer.valueOf( attribute.getKey( ).substring( ATTR_CONTACT_PHONE.length( ) ) );
                                    partnerDTO.setContactPhoneNumber( id, attribute.getValue( ) );
                                }
                                else
                                    if ( attribute.getKey( ).startsWith( ATTR_CONTACT_MAIL ) )
                                    {
                                        int id = Integer.valueOf( attribute.getKey( ).substring( ATTR_CONTACT_MAIL.length( ) ) );
                                        partnerDTO.setContactMail( id, attribute.getValue( ) );
                                    }
                                    else
                                        if ( attribute.getKey( ).startsWith( ATTR_CONTACT_ID ) )
                                        {
                                            int id = Integer.valueOf( attribute.getKey( ).substring( ATTR_CONTACT_ID.length( ) ) );
                                            partnerDTO.setContactId( id, Integer.valueOf( attribute.getValue( ) ) );
                                        }
            }
        }

        return partnerDTO;
    }

    /**
     * Convert entity list to dto list.
     *
     * @param listSource
     *            the list source
     * @return the result list
     */
    public static ResultList<PartnerDTO> convertEntityList( Collection<Provider> listSource )
    {
        ResultList<PartnerDTO> listDest = new ResultList<PartnerDTO>( );

        if ( listSource instanceof ResultList )
        {
            listDest.setTotalResult( ( (ResultList<?>) listSource ).getTotalResult( ) );
        }

        for ( Provider source : listSource )
        {
            listDest.add( convertEntity( source ) );
        }

        return listDest;
    }

    /**
     * @return the classeList
     */
    @Valid
    public List<Contact> getContactList( )
    {
        return contactList;
    }

    /**
     * @param classeList
     *            the classeList to set
     */
    public void setContactList( List<Contact> classeList )
    {
        this.contactList = classeList;
    }

    /**
     * Setter prestation pour le form
     * 
     * @param index
     *            index
     * @param classe
     *            classe
     */

    public void setContactForm( int index, Contact contact )
    {
        while ( index >= contactList.size( ) )
        {
            this.contactList.add( new Contact( ) );
        }

        this.contactList.set( index, contact );
    }

    /**
     * Prestation form
     * 
     * @param index
     *            index
     * @return prestation
     */
    public Contact getContactForm( int index )
    {
        while ( index >= contactList.size( ) )
        {
            this.contactList.add( new Contact( ) );
        }

        return contactList.get( index );
    }

    /**
     * Remove contact from partner with id
     * 
     * @param idContact
     *            the id of the contact which will be deleted
     */
    public void removeContact( int idContact )
    {
        contactList.remove( findContactById( idContact ) );
    }

    /**
     * @return the district
     */
    public Integer getDistrict( )
    {
        return district;
    }

    /**
     * @param district
     *            the district to set
     */
    public void setDistrict( Integer district )
    {
        this.district = district;
    }
}
