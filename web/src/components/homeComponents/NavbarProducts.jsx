import React from 'react';
import {products}  from '../../api/api';
import Page from '../homeComponents/Page'

export default class NavbarProducts extends React.Component {
    constructor(){
        super();
        this.state={
            productsToShow: []
        }
    }

    componentWillMount(){
        products()
        .then(result => { 
          this.setState({productsToShow: result})});
    }

    render(){
        console.log(this.state.productsToShow)
        return(
            <div>
                <Page child={this.state.productsToShow} id="3"/>
            </div>
        );
    }

}