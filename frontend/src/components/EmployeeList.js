import React, { useEffect, useState } from 'react';

export default function EmployeeList({empReady, refresh}) {
    const [employees, setEmployees] = useState([]);
    //TODO this is needed?
    const [error, setError] = useState(false);

    useEffect( () => {
        if(empReady){
            getAllEmployees();
        }
    }, [empReady, refresh]);

    const getAllEmployees = () => {
        fetch("/api/employees")
        .then((response) => {
            if( !response.ok) {
                throw new Error(response.statusText);
            }
            return response.json();
        })
        .then(data => {
            setEmployees(data);
            setError(false);
        })
        .catch((error) => {
            setError(true);
            console.log("Error geting employees lsit. ", error.message);
        });
    };

    return (
        <div className='emp-list'>
            {employees.length>0 && (
                <table>
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Phone Number</th>
                        </tr>
                    </thead>
                    <tbody>
                    {employees.map( employee => (
                        <tr key={employee.id}>
                            <td>{employee.firstName} {employee.lastName}</td>
                            <td>{employee.email}</td>
                            <td>{employee.phoneNumber}</td>
                        </tr>
                    ))}
                    </tbody>                    
                </table>
            )}
            {error && (
                <div>Error when loadsing employee data!</div> 
            )}
        </div>
    )
}
