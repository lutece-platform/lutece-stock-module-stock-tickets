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

    private Integer id;
    @NotEmpty
    private String name;
    private String address;
    private String contactName;
    @NotEmpty
    private String phoneNumber;
    @NotEmpty
    @Email
    private String mail;
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
