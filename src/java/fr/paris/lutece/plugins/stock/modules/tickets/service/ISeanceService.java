/*
 * Copyright (c) 2002-2008, Mairie de Paris
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

import fr.paris.lutece.plugins.stock.business.offer.OfferFilter;
import fr.paris.lutece.plugins.stock.business.offer.OfferGenre;
import fr.paris.lutece.plugins.stock.commons.ResultList;
import fr.paris.lutece.plugins.stock.commons.dao.PaginationProperties;
import fr.paris.lutece.plugins.stock.modules.tickets.business.SeanceDTO;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;


/**
 * The Interface ISeanceService.
 */
@Transactional( rollbackFor = { Exception.class } )
public interface ISeanceService
{

    /**
     * Init this service.
     */
    void init(  );


    /**
     * Modifies a seance.
     * 
     * @param seance The seance to modify in database
     */
    void doSaveOffer( SeanceDTO seance );

    /**
     * Delete a seance.
     * 
     * @param nIdSeance the identifier of the seance to delete
     */
    void doDeleteOffer( int nIdSeance );



	    /**
     * Find by filter.
     * 
     * @param filter the filter
     * @param paginationProperties the pagination properties
     * @return the offer list filtered
     */
    ResultList<SeanceDTO> findByFilter( OfferFilter filter, PaginationProperties paginationProperties );

    /**
     * Update.
     * 
     * @param offer the offer
     */
    void update( SeanceDTO offer );

    /**
     * Find all.
     * 
     * @return the list
     */
    List<SeanceDTO> findAll( );

    /**
     * Return an offer by his id.
     * 
     * @param nIdOffer offer id
     * @return the other
     */
    SeanceDTO findSeanceById( Integer nIdOffer );

	    /**
     * Return all the offer genre.
     * 
     * @return the list of offer genre
     */
	List<OfferGenre> findAllGenre();

    /**
     * List of seance dates for a show.
     * 
     * @param showId id of the show
     * @param filter offer filter
     * @return list of date string
     */
    List<String> findSeanceByShow( Integer showId, OfferFilter filter );

    /**
     * Find seance by date and hour.
     * 
     * @param showId the show id
     * @param dateHour date and hour
     * @return seance list
     */
    List<SeanceDTO> findSeanceByDate( Integer showId, Date dateHour );
}
