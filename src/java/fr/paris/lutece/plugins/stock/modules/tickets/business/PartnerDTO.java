/*
 * Copyright (c) 2002-2012, Mairie de Paris
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
import fr.paris.lutece.plugins.stock.business.provider.Provider;
import fr.paris.lutece.plugins.stock.commons.AbstractDTO;
import fr.paris.lutece.plugins.stock.commons.ResultList;
import fr.paris.lutece.portal.service.spring.SpringContextService;

import java.util.Collection;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.dozer.Mapper;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;


/**
 * DTO for provider
 * 
 * @author abataille
 */
public class PartnerDTO extends AbstractDTO<Provider>
{
    public static final String ATTR_CONTACT = "contactName";
    public static final String ATTR_CONTACT_2 = "contactName2";
    public static final String ATTR_CONTACT_PHONE_NUMBER_2 = "phoneNumber2";
    public static final String ATTR_CONTACT_MAIL_2 = "mail2";
    public static final String ATTR_CONTACT_3 = "contactName3";
    public static final String ATTR_CONTACT_PHONE_NUMBER_3 = "phoneNumber3";
    public static final String ATTR_CONTACT_MAIL_3 = "mail3";

    public static final String ATTR_CONTACT_4 = "contactName4";
    public static final String ATTR_CONTACT_PHONE_NUMBER_4 = "phoneNumber4";
    public static final String ATTR_CONTACT_MAIL_4 = "mail4";

    private Integer id;
    @NotEmpty
    private String name;
    private String address;
    private String contactName;
    private String phoneNumber;
    @NotEmpty
    @Email
    private String mail;

    /**
     * Second contact name
     */
    private String contactName2;
    private String phoneNumber2;
    @Email
    private String mail2;
    
    /**
     * Third contact name
     */
    private String contactName3;
    private String phoneNumber3;
    @Email
    private String mail3;
    
    /**
     * Fourth contact name
     */
    private String contactName4;
    private String phoneNumber4;
    @Email
    private String mail4;
    
    private String comment;

    /**
     * @return the idProvider
     */
    public Integer getId( )
    {
        return id;
    }

    /**
     * @param idProvider the idProvider to set
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
     * @param name the name to set
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
     * @param address the address to set
     */
    public void setAddress( String address )
    {
        this.address = address;
    }

    /**
     * @return the contactName
     */
    public String getContactName( )
    {
        return contactName;
    }

    /**
     * @param contactName the contactName to set
     */
    public void setContactName( String contactName )
    {
        this.contactName = contactName;
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber( )
    {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber( String phoneNumber )
    {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the mail
     */
    public String getMail( )
    {
        return mail;
    }

    /**
     * @param mail the mail to set
     */
    public void setMail( String mail )
    {
        this.mail = mail;
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
     * @param comment the comment to set
     */
    public void setComment( String comment )
    {
        this.comment = comment;
    }


    /**
     * @return the contactName2
     */
    public String getContactName2( )
    {
        return contactName2;
    }

    /**
     * @param contactName2 the contactName2 to set
     */
    public void setContactName2( String contactName2 )
    {
        this.contactName2 = contactName2;
    }

    /**
     * @return the phoneNumber2
     */
    public String getPhoneNumber2( )
    {
        return phoneNumber2;
    }

    /**
     * @param phoneNumber2 the phoneNumber2 to set
     */
    public void setPhoneNumber2( String phoneNumber2 )
    {
        this.phoneNumber2 = phoneNumber2;
    }

    /**
     * @return the mail2
     */
    public String getMail2( )
    {
        return mail2;
    }

    /**
     * @param mail2 the mail2 to set
     */
    public void setMail2( String mail2 )
    {
        this.mail2 = mail2;
    }

    /**
     * @return the contactName3
     */
    public String getContactName3( )
    {
        return contactName3;
    }

    /**
     * @param contactName3 the contactName3 to set
     */
    public void setContactName3( String contactName3 )
    {
        this.contactName3 = contactName3;
    }

    /**
     * @return the phoneNumber3
     */
    public String getPhoneNumber3( )
    {
        return phoneNumber3;
    }

    /**
     * @param phoneNumber3 the phoneNumber3 to set
     */
    public void setPhoneNumber3( String phoneNumber3 )
    {
        this.phoneNumber3 = phoneNumber3;
    }

    /**
     * @return the mail3
     */
    public String getMail3( )
    {
        return mail3;
    }

    /**
     * @param mail3 the mail3 to set
     */
    public void setMail3( String mail3 )
    {
        this.mail3 = mail3;
    }

    /**
     * @return the contactName4
     */
    public String getContactName4( )
    {
        return contactName4;
    }

    /**
     * @param contactName4 the contactName4 to set
     */
    public void setContactName4( String contactName4 )
    {
        this.contactName4 = contactName4;
    }

    /**
     * @return the phoneNumber4
     */
    public String getPhoneNumber4( )
    {
        return phoneNumber4;
    }

    /**
     * @param phoneNumber4 the phoneNumber4 to set
     */
    public void setPhoneNumber4( String phoneNumber4 )
    {
        this.phoneNumber4 = phoneNumber4;
    }

    /**
     * @return the mail4
     */
    public String getMail4( )
    {
        return mail4;
    }

    /**
     * @param mail4 the mail4 to set
     */
    public void setMail4( String mail4 )
    {
        this.mail4 = mail4;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Provider convert( )
    {
        Provider provider = mapper.map( this, Provider.class );

        if ( StringUtils.isNotEmpty( this.getContactName( ) ) )
        {
            provider.getAttributeList( ).add( new ProviderAttribute( ATTR_CONTACT, this.getContactName( ), provider ) );
        }
        
        //Contact 2
        if ( StringUtils.isNotEmpty( this.getContactName2( ) ) )
        {
            provider.getAttributeList( ).add( new ProviderAttribute( ATTR_CONTACT_2, this.getContactName2( ), provider ) );
        }

        if ( StringUtils.isNotEmpty( this.getPhoneNumber2( ) ) )
        {
            provider.getAttributeList( ).add( new ProviderAttribute( ATTR_CONTACT_PHONE_NUMBER_2, this.getPhoneNumber2( ), provider ) );
        }

        if ( StringUtils.isNotEmpty( this.getMail2( ) ) )
        {
            provider.getAttributeList( ).add( new ProviderAttribute( ATTR_CONTACT_MAIL_2, this.getMail2( ), provider ) );
        }
        
        //Contact 3
        if ( StringUtils.isNotEmpty( this.getContactName3( ) ) )
        {
            provider.getAttributeList( ).add( new ProviderAttribute( ATTR_CONTACT_3, this.getContactName3( ), provider ) );
        }

        if ( StringUtils.isNotEmpty( this.getPhoneNumber3( ) ) )
        {
            provider.getAttributeList( ).add( new ProviderAttribute( ATTR_CONTACT_PHONE_NUMBER_3, this.getPhoneNumber3( ), provider ) );
        }

        if ( StringUtils.isNotEmpty( this.getMail3( ) ) )
        {
            provider.getAttributeList( ).add( new ProviderAttribute( ATTR_CONTACT_MAIL_3, this.getMail3( ), provider ) );
        }
        

        //Contact 4
        if ( StringUtils.isNotEmpty( this.getContactName4( ) ) )
        {
            provider.getAttributeList( ).add( new ProviderAttribute( ATTR_CONTACT_4, this.getContactName4( ), provider ) );
        }

        if ( StringUtils.isNotEmpty( this.getPhoneNumber4( ) ) )
        {
            provider.getAttributeList( ).add( new ProviderAttribute( ATTR_CONTACT_PHONE_NUMBER_4, this.getPhoneNumber4( ), provider ) );
        }

        if ( StringUtils.isNotEmpty( this.getMail4( ) ) )
        {
            provider.getAttributeList( ).add( new ProviderAttribute( ATTR_CONTACT_MAIL_4, this.getMail4( ), provider ) );
        }
        return provider;
    }

    /**
     * Return DTO converted from entity bean
     * @param source entity bean
     * @return dto
     */
    public static PartnerDTO convertEntity( Provider source )
    {
        Mapper mapper = (Mapper) SpringContextService.getBean( "mapper" );
        PartnerDTO partnerDTO = mapper.map( source, PartnerDTO.class );

        Set<ProviderAttribute> attributeList = source.getAttributeList( );
        if ( attributeList != null )
        {
            for ( ProviderAttribute attribute : attributeList )
            {
                if ( ATTR_CONTACT.equals( attribute.getKey( ) ) )
                {
                    partnerDTO.setContactName( attribute.getValue( ) );
                }

                if ( ATTR_CONTACT_2.equals( attribute.getKey( ) ) )
                {
                    partnerDTO.setContactName2( attribute.getValue( ) );
                }

                if ( ATTR_CONTACT_PHONE_NUMBER_2.equals( attribute.getKey( ) ) )
                {
                    partnerDTO.setPhoneNumber2( attribute.getValue( ) );
                }

                if ( ATTR_CONTACT_MAIL_2.equals( attribute.getKey( ) ) )
                {
                    partnerDTO.setMail2( attribute.getValue( ) );
                }

                if ( ATTR_CONTACT_3.equals( attribute.getKey( ) ) )
                {
                    partnerDTO.setContactName3( attribute.getValue( ) );
                }

                if ( ATTR_CONTACT_PHONE_NUMBER_3.equals( attribute.getKey( ) ) )
                {
                    partnerDTO.setPhoneNumber3( attribute.getValue( ) );
                }

                if ( ATTR_CONTACT_MAIL_3.equals( attribute.getKey( ) ) )
                {
                    partnerDTO.setMail3( attribute.getValue( ) );
                }

                if ( ATTR_CONTACT_4.equals( attribute.getKey( ) ) )
                {
                    partnerDTO.setContactName4( attribute.getValue( ) );
                }

                if ( ATTR_CONTACT_PHONE_NUMBER_4.equals( attribute.getKey( ) ) )
                {
                    partnerDTO.setPhoneNumber4( attribute.getValue( ) );
                }

                if ( ATTR_CONTACT_MAIL_4.equals( attribute.getKey( ) ) )
                {
                    partnerDTO.setMail4( attribute.getValue( ) );
                }
            }
        }
        return partnerDTO;
    }

    /**
     * Convert entity list to dto list.
     * 
     * @param listSource the list source
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
}
