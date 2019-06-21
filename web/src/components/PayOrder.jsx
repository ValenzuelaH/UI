import React from 'react';

import { findRestaurant } from '../api/api';
import CreditCard from './paymentComponents/CreditCard';
import Cash from './paymentComponents/Cash';
import MercadoPago from './paymentComponents/MercadoPago';
import Select from 'react-select'

import './css/PayOrder.css';

export default class PayOrder extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          id: '',
          password: '',
          code: 0,
          orderSubtotal: 0,
          orderTotal: 0,
          paymentMethods: [],
          selectorPM: [], 
          selectedMenus: [],

          renderCreditCard: false,
          renderCash: false,
          renderMP: false,
          selectedOption: null,

          toShoppingCart: false,
          toOrders: false,
        };
        this.state.id = this.props.location.state.id;
        this.state.password = this.props.location.state.password;
        this.state.code = this.props.location.state.code;
        this.state.selectedMenus = this.props.location.state.selectedMenus;
        this.state.orderSubtotal = this.props.location.state.orderSubtotal;
        this.state.orderTotal = this.props.location.state.orderTotal;
    }

    toOrders = () => {
        this.setState({
            toOrders: true,
            toShoppingCart: false
        })
    };

    backToShoppingCart = () => {
        this.setState({
          toOrders: false,
          toShoppingCart: true
        })
    };

    changePaymentMethod = selectedOption => {
        this.setState({ selectedOption });
        switch (selectedOption.value) {
            case 'CreditCard':
                this.setState({
                    renderCreditCard: true,
                    renderCash: false,
                    renderMP: false,    
                })  
            break;
            case 'DebitCard':
                this.setState({
                    renderCreditCard: true,
                    renderCash: false,
                    renderMP: false,    
                })
            break;
            case 'Cash':
                this.setState({
                    renderCreditCard: false,
                    renderCash: true,
                    renderMP: false,    
                })
            break;
            case 'MercadoPago':
                this.setState({
                    renderCreditCard: false,
                    renderCash: false,
                    renderMP: true,    
                })
            break;              
            default:
                this.setState({
                    renderCreditCard: false,
                    renderCash: false,
                    renderMP: false,    
                })
            break;
          }
      };

    unitPrice(givenMenu){
        return (givenMenu.productsOfMenu.reduce(
                    function(prev, cur) {return prev + cur.price; }
                    , 0)); 
    }

    discountPrice(givenMenu){
        var priceWithDiscount = this.unitPrice(givenMenu);
        if(givenMenu.discount.name === "DescuentoPorMonto"){
            priceWithDiscount = priceWithDiscount - givenMenu.discount.value;       
            }else{
                if(givenMenu.discount.name === "DescuentoPorPorcentaje"){
                    priceWithDiscount = priceWithDiscount - (priceWithDiscount * givenMenu.discount.value / 100);
                }   
            }                            
        return(priceWithDiscount);
    }

    renderSelectedMenus(){
    return((menus) => <li key={menus.menu.code}>
                            <div className="grid-container4">
                                <div>{menus.menu.name} </div>
                                <div>{menus.ammount} </div>
                                <div>{this.unitPrice(menus.menu)}$</div>
                                <div>{this.unitPrice(menus.menu) * menus.ammount}$</div>
                                <div>{this.discountPrice(menus.menu) * menus.ammount}$</div>
                            </div>
                        </li>            
                )
    }

    componentDidMount(){    
        findRestaurant(0) //TENGO QUE RECIBIR POR PROPS EL CODE DEL RESTAURANT
        .then(result => {
            var tempObj = result.availablePaymentMethods;
            this.setState({    
            paymentMethods: tempObj })    
        for (var i = 0; i < Object.keys(tempObj).length; i++) {
            var tempObj2 = { value: tempObj[i].typePM, label: tempObj[i].typePM }
            this.state.selectorPM.push(tempObj2);
        }})   
    }

    render() {
        const { selectedOption } = this.state;  
        return (<div>
                <div className="grid-container0">
                    <div className="card">          
                        <h2>Seleccione su método de pago: </h2>     
                        <div>
                            <Select 
                                value={selectedOption}
                                onChange={this.changePaymentMethod}
                                options={this.state.selectorPM} />
                            <ul>
                                { this.state.renderCreditCard && <CreditCard /> }
                                { this.state.renderCash && <Cash /> }
                                { this.state.renderMP && <MercadoPago /> }
                            </ul>    
                        </div>
                    </div>
                    <div className="card">
                        <h2>Su pedido: </h2>
                            <div>
                                <ul>
                                    <div className="grid-container3">                            
                                        <div>Menú </div>
                                        <div>Cant.</div> 
                                        <div>P/Uni</div>
                                        <div>Tot.</div>
                                        <div>C/Desc.</div>
                                    </div>
                                    {this.state.selectedMenus.map(this.renderSelectedMenus())}    
                                                    
                                </ul>
                            </div>
                        <div class="righty">Subtotal del Pedido: {this.state.orderSubtotal}$</div>
                        <div class="righty"><h4>Total del Pedido: {this.state.orderTotal}$</h4></div>
                        <button className="btn btn-danger" onClick={this.backToOrders}>Volver al Carrito</button>
                        <button className="btn btn-success" onClick={this.toPayment}>Realizar el Pago</button>
                    </div>
                </div>
                
            </div>
        )
    }
}