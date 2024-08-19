import { ColumnDef } from "../../common-utils/components/table/table.config";

export const contractTable = (cellParams:any=null):ColumnDef[]=>{
    return[
        {
            field: 'id',
            header: 'ID',
            
            hide: true,
            download: false,
            sortable: true,
        },
        {
            field: 'name',
            header: 'Name',
            cellClicked: (data: any) => cellParams.cellClicked(data),
            hide: false,
            download: true,
            sortable: true,
            filter: {
                type: "text",
                isEnabled: true,
                numberOnly: false
            }
        },
        {
            field:'owner',
            header:'Owner',
            hide:false,
            download:true,
            sortable:true
        }, 
        {
            field:'type',
            header:'Type',
            hide:false,
            download:true,
            sortable:true
        },      
        {
            field: 'createdBy',
            header: 'Created By',
            hide: true,
            download: true,
            sortable: true,
        },
        {
            field: 'orgId',
            header: 'orgId',
            hide: true,
            download: true,
            sortable: true,
        },
        {
            field: 'createdDatetime',
            header: 'Created Date Time',
            hide: true,
            download: true,
            sortable: true,
        },
        {
            field: 'lastModifiedBy',
            header: 'Last Modified By',
            hide: true,
            download: true,
            sortable: true,
        },
        {
            field: 'lastModifiedDatetime',
            header: 'Last Modified Date time',
            hide: true,
            download: true,
            sortable: true,
        },
    ];
};