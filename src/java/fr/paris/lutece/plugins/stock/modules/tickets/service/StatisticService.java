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

import fr.paris.lutece.plugins.stock.business.product.IProductDAO;
import fr.paris.lutece.plugins.stock.business.product.Product;
import fr.paris.lutece.plugins.stock.business.purchase.IPurchaseDAO;
import fr.paris.lutece.plugins.stock.business.purchase.Purchase;
import fr.paris.lutece.plugins.stock.modules.tickets.business.IProductStatisticDAO;
import fr.paris.lutece.plugins.stock.modules.tickets.business.IPurchaseStatisticDAO;
import fr.paris.lutece.plugins.stock.modules.tickets.business.ProductStatistic;
import fr.paris.lutece.plugins.stock.modules.tickets.business.PurchaseStatistic;
import fr.paris.lutece.plugins.stock.modules.tickets.business.ReservationDTO;
import fr.paris.lutece.plugins.stock.modules.tickets.business.ResultStatistic;
import fr.paris.lutece.plugins.stock.modules.tickets.business.ShowDTO;
import fr.paris.lutece.plugins.stock.modules.tickets.utils.Constants;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.util.date.DateUtil;

import org.apache.commons.lang3.StringUtils;

import org.apache.log4j.Logger;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

import org.jfree.data.time.Day;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Week;
import org.jfree.data.xy.XYDataset;

import java.awt.Color;

import java.sql.Timestamp;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * The service Statistic.
 */
public class StatisticService implements IStatisticService
{
    /** The CONSTAN t_ grou p_ b y_ day. */
    public static final String CONSTANT_GROUP_BY_DAY = "0";

    /** The CONSTAN t_ grou p_ b y_ week. */
    public static final String CONSTANT_GROUP_BY_WEEK = "1";

    /** The CONSTAN t_ grou p_ b y_ month. */
    public static final String CONSTANT_GROUP_BY_MONTH = "2";

    /** The EXPOR t_ heade r_ date. */
    public static final String EXPORT_HEADER_DATE = "module.stock.billetterie.manage_statistics.csv.headerDate";

    /** The EXPOR t_ heade r_ n b_ products. */
    public static final String EXPORT_HEADER_NB_PRODUCTS = "module.stock.billetterie.manage_statistics.csv.headerNbProducts";

    /** The EXPOR t_ heade r_ group e_ day. */
    public static final String EXPORT_HEADER_GROUPE_DAY = "module.stock.billetterie.manage_statistics.csv.groupDay";

    /** The EXPOR t_ heade r_ group e_ week. */
    public static final String EXPORT_HEADER_GROUPE_WEEK = "module.stock.billetterie.manage_statistics.csv.groupWeek";

    /** The EXPOR t_ heade r_ group e_ month. */
    public static final String EXPORT_HEADER_GROUPE_MONTH = "module.stock.billetterie.manage_statistics.csv.groupMonth";

    /** The EMPT y_ string. */
    public static final String EMPTY_STRING = "";
    private static final Logger LOGGER = Logger.getLogger( StatisticService.class );

    /** The _dao product statistic. */
    @Inject
    private IProductStatisticDAO _daoProductStatistic;

    /** The _dao purchase statistic. */
    @Inject
    private IPurchaseStatisticDAO _daoPurchaseStatistic;

    /** The _dao product. */
    @Inject
    @Named( "stock-tickets.showDAO" )
    private IProductDAO _daoProduct;

    /** The _dao purchase. */
    @Inject
    @Named( "stock-tickets.reservationDAO" )
    private IPurchaseDAO _daoPurchase;

    /**
     * return a timestamp Object which correspond to the timestamp in parameter add with a number of times unit (day,week,month)specify in strTimesUnit .
     * 
     * @param timestamp
     *            date
     * @param strTimesUnit
     *            (day,week,month)
     * @param nDecal
     *            the number of timesUnit
     * @return a timestamp Object which correspond with the string specified in parameter add with a number of times unit (day,week,month)specify in
     *         strTimesUnit.
     */
    public static Timestamp addStatisticInterval( Timestamp timestamp, String strTimesUnit, int nDecal )
    {
        int nTimesUnit = Calendar.DAY_OF_MONTH;

        if ( strTimesUnit.equals( CONSTANT_GROUP_BY_WEEK ) )
        {
            nTimesUnit = Calendar.WEEK_OF_MONTH;
        }
        else
            if ( strTimesUnit.equals( CONSTANT_GROUP_BY_MONTH ) )
            {
                nTimesUnit = Calendar.MONTH;
            }

        Calendar caldate = new GregorianCalendar( );
        caldate.setTime( timestamp );
        caldate.set( Calendar.MILLISECOND, 0 );
        caldate.set( Calendar.SECOND, 0 );
        caldate.set( Calendar.HOUR_OF_DAY, caldate.getActualMaximum( Calendar.HOUR_OF_DAY ) );
        caldate.set( Calendar.MINUTE, caldate.getActualMaximum( Calendar.MINUTE ) );
        caldate.add( nTimesUnit, nDecal );

        return new Timestamp( caldate.getTimeInMillis( ) );
    }

    /**
     * Compare two timestamp and return true if they have the same times unit(Day,week,month).
     *
     * @param timestamp1
     *            timestamp1
     * @param timestamp2
     *            timestamp2
     * @param strTimesUnit
     *            (day,week,month)
     * @return Compare two timestamp and return true if they have the same times unit(Day,week,month)
     */
    public static boolean sameDate( Timestamp timestamp1, Timestamp timestamp2, String strTimesUnit )
    {
        Calendar caldate1 = new GregorianCalendar( );
        caldate1.setTime( timestamp1 );

        Calendar caldate2 = new GregorianCalendar( );
        caldate2.setTime( timestamp2 );

        return strTimesUnit.equals( CONSTANT_GROUP_BY_DAY ) && ( caldate1.get( Calendar.YEAR ) == caldate2.get( Calendar.YEAR ) )
                && ( caldate1.get( Calendar.DAY_OF_YEAR ) == caldate2.get( Calendar.DAY_OF_YEAR ) );
    }

    /**
     * {@inheritDoc}
     */
    public void doManageProductSaving( ShowDTO productDTO, Locale locale )
    {
        // On supprime tous les objets ProductStatistic
        doRemoveProductStatisticByIdProduct( productDTO.getId( ) );

        // Le produit vient d'etre insere en base. On insere des produitStatistic
        SimpleDateFormat formatter = new SimpleDateFormat( Constants.FORMAT_DATE );

        try
        {
            Calendar calendarDebut = new GregorianCalendar( );
            Calendar calendarFin = new GregorianCalendar( );
            calendarDebut.setTime( formatter.parse( productDTO.getStartDate( ) ) );
            calendarFin.setTime( formatter.parse( productDTO.getEndDate( ) ) );

            do
            {
                ProductStatistic productStatistic = new ProductStatistic( );
                productStatistic.setDayOfYear( calendarDebut.get( Calendar.DAY_OF_YEAR ) );
                productStatistic.setWeek( calendarDebut.get( Calendar.WEEK_OF_YEAR ) );
                productStatistic.setMonth( ( calendarDebut.get( Calendar.MONTH ) + 1 ) );
                productStatistic.setYear( calendarDebut.get( Calendar.YEAR ) );
                productStatistic.setDate( new Timestamp( calendarDebut.getTime( ).getTime( ) ) );

                Product product = new Product( );
                product.setId( productDTO.getId( ) );
                productStatistic.setProduct( product );

                _daoProductStatistic.create( productStatistic );

                calendarDebut.set( Calendar.DAY_OF_YEAR, calendarDebut.get( Calendar.DAY_OF_YEAR ) + 1 );
            }
            while ( calendarDebut.before( calendarFin ) || calendarDebut.equals( calendarFin ) );
        }
        catch( ParseException e )
        {
            LOGGER.warn( I18nService.getLocalizedString( "module.stock.ticket.message.error.parsing", locale ), e );
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<ResultStatistic> getProductStatistic( String strTimesUnit, String strDateDebut, String strDateFin )
    {
        return _daoProductStatistic.getAllResultStatisticByParameters( strTimesUnit, doChangeDateFormat( strDateDebut ), doChangeDateFormat( strDateFin ) );
    }

    /**
     * create a JFreeChart Graph function of the statistic form submit.
     *
     * @param listStatistic
     *            the list of statistic of form submit
     * @param strLabelX
     *            the label of axis x
     * @param strLableY
     *            the label of axis x
     * @param strTimesUnit
     *            the times unit of axis x(Day,Week,Month)
     * @return a JFreeChart Graph function of the statistic form submit
     */
    public static JFreeChart createXYGraph( List<ResultStatistic> listStatistic, String strLabelX, String strLableY, String strTimesUnit )
    {
        XYDataset xyDataset = createDataset( listStatistic, strTimesUnit );
        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart( EMPTY_STRING, strLabelX, strLableY, xyDataset, false, false, false );
        jfreechart.setBackgroundPaint( Color.white );

        XYPlot xyplot = jfreechart.getXYPlot( );

        xyplot.setBackgroundPaint( Color.white );
        xyplot.setBackgroundPaint( Color.lightGray );
        xyplot.setDomainGridlinePaint( Color.white );
        xyplot.setRangeGridlinePaint( Color.white );

        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) xyplot.getRenderer( );
        renderer.setBaseShapesVisible( true );
        renderer.setSeriesFillPaint( 0, Color.RED );
        renderer.setUseFillPaint( true );

        return jfreechart;
    }

    /**
     * create graph dataset function of the statistic form submit.
     *
     * @param listStatistic
     *            the list of statistic of form submit
     * @param strTimesUnit
     *            the times unit of axis x(Day,Week,Month)
     * @return create graph dataset function of the statistic form submit
     */
    private static XYDataset createDataset( List<ResultStatistic> listStatistic, String strTimesUnit )
    {
        TimeSeries series = null;

        if ( strTimesUnit.equals( CONSTANT_GROUP_BY_DAY ) )
        {
            series = new TimeSeries( EMPTY_STRING, Day.class );

            for ( ResultStatistic statistic : listStatistic )
            {
                series.add( new Day( (Date) statistic.getStatisticDate( ) ), statistic.getNumberResponse( ) );
            }
        }
        else
            if ( strTimesUnit.equals( CONSTANT_GROUP_BY_WEEK ) )
            {
                series = new TimeSeries( EMPTY_STRING, Week.class );

                for ( ResultStatistic statistic : listStatistic )
                {
                    series.add( new Week( (Date) statistic.getStatisticDate( ) ), statistic.getNumberResponse( ) );
                }
            }

            else
                if ( strTimesUnit.equals( CONSTANT_GROUP_BY_MONTH ) )
                {
                    series = new TimeSeries( EMPTY_STRING, Month.class );

                    for ( ResultStatistic statistic : listStatistic )
                    {
                        series.add( new Month( (Date) statistic.getStatisticDate( ) ), statistic.getNumberResponse( ) );
                    }
                }

        TimeSeriesCollection dataset = new TimeSeriesCollection( );
        dataset.addSeries( series );

        return dataset;
    }

    /**
     * {@inheritDoc}
     */
    public Integer getCountProductsByDates( String strDateDebut, String strDateFin )
    {
        return _daoProductStatistic.getCountProductsByDates( doChangeDateFormat( strDateDebut ), doChangeDateFormat( strDateFin ) );
    }

    /**
     * Change the format of data date. User format to database format
     * 
     * @param dateEntree
     *            the user format date
     * @return the database date format
     */
    private String doChangeDateFormat( String dateEntree )
    {
        if ( ( dateEntree != null ) && !dateEntree.equals( StringUtils.EMPTY ) )
        {
            SimpleDateFormat dfEntree = new SimpleDateFormat( Constants.FORMAT_DATE );
            SimpleDateFormat dfBdd = new SimpleDateFormat( "yyyy-MM-dd" );
            Date dateDebut;
                try
                {
                    dateDebut = dfEntree.parse( dateEntree );
                    return dfBdd.format( dateDebut );
                }
                catch (ParseException e)
                {
                    return null;
                }
        }
        else
        {
            return null;
        }
    }

    /**
     * Converts the informations to export into a list of String[].
     *
     * @param listData
     *            The list data to convert
     * @param strTimesUnit
     *            the str times unit
     * @param locale
     *            the locale
     * @return a list of String[] corresponding to listData or null if there is no data to convert
     */
    public static List<String [ ]> buildListToCSVWriter( Collection<ResultStatistic> listData, String strTimesUnit, Locale locale )
    {
        if ( ( listData == null ) || listData.isEmpty( ) )
        {
            return new ArrayList<>( );
        }

        List<String [ ]> returnList = new ArrayList<>( );

        // Build the header
        String strTimesHeader = EXPORT_HEADER_GROUPE_MONTH;

        if ( strTimesUnit.equals( CONSTANT_GROUP_BY_DAY ) )
        {
            strTimesHeader = EXPORT_HEADER_GROUPE_DAY;
        }
        else
            if ( strTimesUnit.equals( CONSTANT_GROUP_BY_WEEK ) )
            {
                strTimesHeader = EXPORT_HEADER_GROUPE_WEEK;
            }

        String [ ] header = {
                I18nService.getLocalizedString( EXPORT_HEADER_DATE, locale ),

                I18nService.getLocalizedString( EXPORT_HEADER_NB_PRODUCTS, locale ) + " " + I18nService.getLocalizedString( strTimesHeader, locale ),
        };
        returnList.add( header );

        // Build lines
        for ( ResultStatistic currentResultStatistic : listData )
        {
            String [ ] line = new String [ header.length];
            int i = 0;
            line [i++] = DateUtil.getDateString( currentResultStatistic.getStatisticDate( ), locale );
            line [i++] = String.valueOf( currentResultStatistic.getNumberResponse( ) );
            returnList.add( line );
        }

        return returnList;
    }

    /**
     * {@inheritDoc}
     */
    public void doManagePurchaseSaving( ReservationDTO purchaseDTO, Locale locale )
    {
        // On supprime tous les objets ProductStatistic
        doRemovePurchaseStatisticByIdPurchase( purchaseDTO.getId( ) );

        // La reservation vient d'etre insere en base. On insere un purchaseStatistic
        SimpleDateFormat formatter = new SimpleDateFormat( Constants.FORMAT_DATE );

        try
        {
            Calendar calendar = new GregorianCalendar( );
            calendar.setTime( formatter.parse( purchaseDTO.getDate( ) ) );

            PurchaseStatistic purchaseStatistic = new PurchaseStatistic( );
            purchaseStatistic.setDayOfYear( calendar.get( Calendar.DAY_OF_YEAR ) );
            purchaseStatistic.setWeek( calendar.get( Calendar.WEEK_OF_YEAR ) );
            purchaseStatistic.setMonth( ( calendar.get( Calendar.MONTH ) + 1 ) );
            purchaseStatistic.setYear( calendar.get( Calendar.YEAR ) );
            purchaseStatistic.setDate( new Timestamp( calendar.getTime( ).getTime( ) ) );

            Purchase purchase = new Purchase( );
            purchase.setId( purchaseDTO.getId( ) );

            purchaseStatistic.setPurchase( purchase );

            _daoPurchaseStatistic.create( purchaseStatistic );
        }
        catch( ParseException e )
        {
            LOGGER.warn( I18nService.getLocalizedString( "module.stock.ticket.message.error.parsing", locale ), e );
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<ResultStatistic> getPurchaseStatistic( String strTimesUnit, String strDateDebut, String strDateFin )
    {
        return _daoPurchaseStatistic.getAllResultStatisticByParameters( strTimesUnit, doChangeDateFormat( strDateDebut ), doChangeDateFormat( strDateFin ) );
    }

    /**
     * {@inheritDoc}
     */
    public Integer getCountPurchasesByDates( String strDateDebut, String strDateFin )
    {
        return _daoPurchaseStatistic.getCountPurchasesByDates( doChangeDateFormat( strDateDebut ), doChangeDateFormat( strDateFin ) );
    }

    /**
     * {@inheritDoc}
     */
    public Integer getCountProductALAffiche( )
    {
        Calendar currentCalendar = new GregorianCalendar( );

        String strCurrentDate = currentCalendar.get( Calendar.YEAR ) + "-" + ( currentCalendar.get( Calendar.MONTH ) + 1 ) + "-"
                + currentCalendar.get( Calendar.DAY_OF_MONTH );

        return _daoProduct.getCountProductALAfficheByDate( strCurrentDate );
    }

    /**
     * {@inheritDoc}
     */
    public Integer getCountProductAVenir( )
    {
        Calendar currentCalendar = new GregorianCalendar( );

        String strCurrentDate = currentCalendar.get( Calendar.YEAR ) + "-" + ( currentCalendar.get( Calendar.MONTH ) + 1 ) + "-"
                + currentCalendar.get( Calendar.DAY_OF_MONTH );

        return _daoProduct.getCountProductAVenirByDate( strCurrentDate );
    }

    /**
     * {@inheritDoc}
     */
    public Integer getCountPurchaseOfDay( )
    {
        Calendar currentCalendar = new GregorianCalendar( );

        String strCurrentDate = currentCalendar.get( Calendar.YEAR ) + "-" + ( currentCalendar.get( Calendar.MONTH ) + 1 ) + "-"
                + currentCalendar.get( Calendar.DAY_OF_MONTH );

        return _daoPurchase.getCountPurchaseByBeginDateAndLastDate( strCurrentDate, strCurrentDate );
    }

    /**
     * {@inheritDoc}
     */
    public Integer getCountPurchaseOfMonth( )
    {
        Calendar currentCalendar = new GregorianCalendar( );

        String strDateDebut = currentCalendar.get( Calendar.YEAR ) + "-" + ( currentCalendar.get( Calendar.MONTH ) + 1 ) + "-01";

        String strDateFin = currentCalendar.get( Calendar.YEAR ) + "-" + ( currentCalendar.get( Calendar.MONTH ) + 1 ) + "-"
                + currentCalendar.getActualMaximum( Calendar.DAY_OF_MONTH );

        return _daoPurchase.getCountPurchaseByBeginDateAndLastDate( strDateDebut, strDateFin );
    }

    /**
     * {@inheritDoc}
     */
    public void doRemoveProductStatisticByIdProduct( Integer nIdProduct )
    {
        List<ProductStatistic> listeProductStatistic = _daoProductStatistic.getAllByIdProduct( nIdProduct );

        for ( ProductStatistic productStatistic : listeProductStatistic )
        {
            _daoProductStatistic.remove( productStatistic.getId( ) );
        }
    }

    /**
     * {@inheritDoc}
     */
    public void doRemovePurchaseStatisticByIdPurchase( Integer nIdPurchase )
    {
        List<PurchaseStatistic> listePurchaseStatistic = _daoPurchaseStatistic.getAllByIdPurchase( nIdPurchase );

        for ( PurchaseStatistic purchaseStatistic : listePurchaseStatistic )
        {
            _daoPurchaseStatistic.remove( purchaseStatistic.getId( ) );
        }
    }

    public void doManageProductSaving(ShowDTO product) {
        // TODO Auto-generated method stub
        
    }

}
