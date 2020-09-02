import React, { useState, useEffect } from "react";
import MaterialTable from "material-table";
import Trend from './Trend'


function StocksTable() {
        const [data, setData] = useState<object>({});


        useEffect(() => {

                const ws = new WebSocket('ws://localhost:8080/stock_indices')

                ws.onopen = () => {
                        console.log('connected')
                }

                ws.onclose = () => {
                        console.log('disconnected')
                }

                ws.onmessage = (event) => {
                        const ev = JSON.parse(event.data);
                        ev.price = ev.price.toFixed(2);
                        data[ev.ticker as string] = ev;
                        setData({...data});

                        console.log(event.data);
                }


        },[]);


    return (
        <MaterialTable
                columns={[
                        { title: "Symbol", field: "ticker", defaultSort: 'asc' },
                        { title: "Price", field: "price" },
                        {
                            title: "Trend",
                            field: "trend",
                            render: rowData => <Trend trend={rowData.trend}/>
                        }
                ]}
                options={{
                        search: false,
                        paging: false,
                        toolbar: false,
                        headerStyle: {backgroundColor: '#282c34', color: 'white'},
                }}
                data={Object.values(data).sort()}
        />
    );
}

export default StocksTable;