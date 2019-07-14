import React from 'react';
import '../css/Body.css';
import Popup from './PopUp';

export default class ContentSearchResult extends React.Component {
  constructor(props) {
    super(props);
    console.log("H");
    console.log(this.props.k);
    this.state = {
      showPopup: false,
      things: this.props.k
    }  
    console.log(this.state.things);
  }
closePopup(){
  this.setState({  
    showPopup: false  
  });
}

togglePopup() {  
  this.setState({  
       showPopup: !this.state.showPopup  
  });  
   } 
  
  render() {
    
    const things = this.state.things.map((thing, i) => {

      return(
        
        <div className="card mt-4 col-md-4" key={i}>
            <div className="card-headercard-title text-center">
              <h4>{thing.name}</h4>
              <div className="card-body">
                {"Descripción: " + thing.description}
                <button type="button" className="btn btn-primary btn-block" onClick={this.togglePopup.bind(this)}> Hacé tu pedido!</button>  

                {this.state.showPopup &&
                <Popup  
                      closePopup={this.togglePopup.bind(this)}
                      code = {thing.code}
                />  
                }  
              </div> 
            
            </div>
        </div>
    
        
        )})

        return(
          <div>
              {
                things 
              }
          </div>
        )
      

  }
}