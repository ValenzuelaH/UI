import React from 'react';

import { getPendingOrdersFrom } from '../api/api'
import { getHistoricOrdersFrom } from '../api/api'
import {mySearch} from '../api/api'
import HistoricOrder from './HistoricOrders.jsx'
import PendingOrder from './PendingOrder.jsx'
import './css/Orders.css';
import './css/Body.css'

export default class Orders extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          id: '',
          code: -1,
          pendingOrders: [],  
          historicOrders: [],
          toShoppingCart: false
        }; 
        this.state.id = this.props.user;  
   
    }

    componentDidMount(){
      getPendingOrdersFrom(this.state.id)
        .then(result => { 
          this.setState({pendingOrders: result})});
      getHistoricOrdersFrom(this.state.id)
        .then(result => {
          this.setState({historicOrders: result})});
      if(this.props.location !== undefined){
          const { q } = this.props.location.state
          if (q !== ''){
                  mySearch(q)
                  .then(result => { 
                      this.setState({toShow: result, mustBeRender:true})});
                  }
     
          }    
      }

      
   
    render() {    
      
      const mappingOrderCode = (order) => (<PendingOrder key = {order.code_order_complete}
                                                  id = {this.state.id}
                                                  password = {this.state.password}
                                                  code_order_complete = {order.code_order_complete}
                                                  restaurantName = {order.restaurantName}
                                                  menus = {order.menus}
                                                   />)

      const mappingHistoricOrderCode = (order) => (<HistoricOrder key = {order.code_order_complete}
                                                  id = {this.state.id}
                                                  password = {this.state.password}
                                                  code_order_complete = {order.code_order_complete}
                                                  restaurantName = {order.restaurantName}
                                                  menus = {order.menus}
                                                  />)

        return(
            <div>  
              <div className="ribbon">
                  <div><h1>{this.state.id}! Logueado! </h1></div>  
              </div>
                <div>Ordenes Pendientes</div>
                  <ul>
                    <div className="grid-container3">                      
                      {this.state.pendingOrders.map(mappingOrderCode)}                      
                    </div>
                  </ul>                                          
                <div>Ordenes Históricas</div>
                  <ul>
                    <div className="grid-container3">
                      {this.state.historicOrders.map(mappingHistoricOrderCode)}
                    </div>
                  </ul>
            </div>    
        )}
  }
