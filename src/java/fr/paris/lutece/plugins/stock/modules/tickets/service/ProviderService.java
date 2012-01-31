/*
 * Copyright (c) 2002-2011, Mairie de Paris
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
package fr.paris.lutece.plugins.stock.modules.tickets.service;

import fr.paris.lutece.plugins.stock.business.provider.IProviderDAO;
import fr.paris.lutece.plugins.stock.business.provider.Provider;
import fr.paris.lutece.plugins.stock.business.provider.ProviderFilter;
import fr.paris.lutece.plugins.stock.commons.ResultList;
import fr.paris.lutece.plugins.stock.commons.dao.PaginationProperties;
import fr.paris.lutece.plugins.stock.commons.exception.BusinessException;
import fr.paris.lutece.plugins.stock.modules.tickets.business.PartnerDTO;
import fr.paris.lutece.plugins.stock.service.impl.AbstractService;

import java.util.List;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;


/**
 *
 * ProviderService
 *
 */
@Transactional( readOnly = false )
public final class ProviderService extends AbstractService implements IProviderService
{
	// MESSAGE
	private static final String MESSAGE_ERROR_PARTNER_UNIQUE_BY_NAME = "module.stock.billetterie.save_partner.name.unique";
	
    private static ProviderService _instance = new ProviderService(  );
	@Inject
	private IProviderDAO _daoProvider;

    /**
     * Constructor
     */
    private ProviderService(  )
    {
    }

    /**
     * Return the provider service instance
     * @return the provider service instance
     */
    public static ProviderService getInstance(  )
    {
        return _instance;
    }


    /**
     * {@inheritDoc}
     */
    public void init(  )
    {
    }


    /**
     * {@inheritDoc}
     */
    @Transactional( readOnly = false )
    public void doSaveProvider( PartnerDTO provider )
    {

        // BO-CU01-E01-RGE04 : Le nom doit etre unique
        ProviderFilter providerFilter = new ProviderFilter( );
        providerFilter.setName( provider.getName( ) );
        ResultList<Provider> listPartner = this._daoProvider.findByFilter( providerFilter, null );
        if ( listPartner != null && !listPartner.isEmpty( ) )
        {
            if ( ( provider.getId( ) == null || provider.getId( ) < 0 || listPartner.get( 0 ).getId( ) != provider
                    .getId( ) ) )
            {
                throw new BusinessException( provider, MESSAGE_ERROR_PARTNER_UNIQUE_BY_NAME );
            }
        }

        if ( provider.getId( ) != null && provider.getId( ) > 0 )
        {
            _daoProvider.update( provider.convert( ) );
        }
        else
        {
            _daoProvider.create( provider.convert( ) );
        }

    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public void doDeleteProvider( int nIdProvider )
    {
        _daoProvider.remove( nIdProvider );
    }



	/**
	 * {@inheritDoc}
	 */
    public PartnerDTO findByIdWithProducts( int nId )
    {
        return PartnerDTO.convertEntity( _daoProvider.findByIdWithProducts( nId ) );
	}


    /**
     * {@inheritDoc}
     */
    public PartnerDTO findById( int nId )
    {
        return PartnerDTO.convertEntity( _daoProvider.findById( nId ) );
    }

	/**
	 * {@inheritDoc}
	 */
    public ResultList<PartnerDTO> findByFilter( ProviderFilter filter, PaginationProperties paginationProperties )
    {
        return PartnerDTO.convertEntityList( _daoProvider.findByFilter( filter, paginationProperties ) );
	}

	/**
	 * {@inheritDoc}
	 */
    public List<PartnerDTO> findAll( )
    {
        return PartnerDTO.convertEntityList( _daoProvider.findAll( ) );
    }
}
