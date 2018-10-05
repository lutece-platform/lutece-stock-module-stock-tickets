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
package fr.paris.lutece.plugins.stock.modules.tickets.service;

import fr.paris.lutece.plugins.stock.business.purchase.PurchaseFilter;
import fr.paris.lutece.plugins.stock.commons.ResultList;
import fr.paris.lutece.plugins.stock.commons.dao.PaginationProperties;
import fr.paris.lutece.plugins.stock.modules.tickets.business.ReservationDTO;

import java.util.List;

/**
 * The Interface IPurchaseService.
 */
public interface IPurchaseService
{
    /**
     * Init this service.
     */
    void init( );

    /**
     * Modifies a reservation.
     *
     * @param reservation
     *            The reservation to modify in database
     * @param sessionId
     *            the session id
     * @return the reservation dto
     */
    ReservationDTO doSavePurchase( ReservationDTO reservation, String sessionId );

    /**
     * Save a list of reservation.
     *
     * @param reservation
     *            list of reservation
     * @param sessionId
     *            the session id
     * @return the list
     */
    List<ReservationDTO> doSavePurchaseList( List<ReservationDTO> reservation, String sessionId );

    /**
     * Release a list of reservation.
     *
     * @param bookingList
     *            the booking list
     * @param sessionId
     *            the session id
     */
    void doCancelPurchaseList( List<ReservationDTO> bookingList, String sessionId );

    /**
     * Delete a reservation.
     *
     * @param nIdReservation
     *            the identifier of the reservation to delete
     */
    void doDeletePurchase( int nIdReservation );

    /**
     * Find by filter.
     *
     * @param filter
     *            the filter
     * @param paginationProperties
     *            the pagination properties
     * @return the purchase list filtered
     */
    ResultList<ReservationDTO> findByFilter( PurchaseFilter filter, PaginationProperties paginationProperties );

    /**
     * Find by filter without pagination properties.
     *
     * @param filter
     *            the filter
     * @return the purchase list filtered
     */
    ResultList<ReservationDTO> findByFilter( PurchaseFilter filter );

    /**
     * Update.
     *
     * @param purchase
     *            the purchase
     */
    void update( ReservationDTO purchase );

    /**
     * Find all.
     *
     * @return the list
     */
    List<ReservationDTO> findAll( );

    /**
     * Return an purchase by his id.
     *
     * @param nIdPurchase
     *            purchase id
     * @return the other
     */
    ReservationDTO findById( Integer nIdPurchase );

    /**
     * Return the number of reservation for an offer and an agent.
     *
     * @param id
     *            offer id
     * @param idOfferGenre
     *            the id offer genre
     * @param userName
     *            agent userName
     * @return number of reservation
     */
    Integer getNumberOfReservationByIdProductAndUserName( Integer id, Integer idOfferGenre, String userName );
}
