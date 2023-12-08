import React from 'react';
import { Link } from 'react-router-dom';

function DropdownMenu() {
 return (
   <div className="dropdown-menu">
     <ul>
       <li>All movies</li>
       <li>By genre</li>
     </ul>
   </div>
 );
}

export default DropdownMenu;
