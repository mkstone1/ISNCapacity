
begin{

    function Test-PsRemoting { 
        param( 
            [Parameter(Mandatory = $true)] $computername,
            [Parameter(Mandatory = $true)] $creds 
        )

        Write-Verbose "$RemoteComputer : Testing if PSRemoting is possible." -Verbose
        try{ 
            $errorActionPreference = "Stop" 
            $result = Invoke-Command -ComputerName $computername -Credential $creds { 1 }
            Write-Verbose "$RemoteComputer : PSRemoting is possible." -Verbose
        }
        catch{ 
            Write-Verbose "$RemoteComputer : PSRemoting is not possible." -Verbose
            Write-Verbose $_ -Verbose
            return $false 
        }
        $true    
    }

    $User = 'XXXXXXXX'
    $Password = ConvertTo-SecureString "XXXXXXX" -AsPlainText -Force 
    $ComputerList = Get-Content -Path 'C:\Users\marks\IdeaProjects\ISNCapacity\staticinfo\ServerList.txt'
    
}

process{

    for($i = 0; $i -lt $ComputerList.Length; $i++){
        $RemoteComputer = $ComputerList[$i]
	    $Credential = New-Object -TypeName "System.Management.Automation.PSCredential" -ArgumentList $User, $Password

        if(Test-PsRemoting $RemoteComputer $Credential){
            Write-Verbose "$RemoteComputer : Creating new PSSession." -Verbose
            $Sess = New-PSSession $RemoteComputer -Credential $Credential

            Write-Verbose "Getting VM Data from $RemoteComputer" -Verbose

            

            $localPath = "C:\Users\marks\IdeaProjects\ISNCapacity\staticinfo\$RemoteComputer\"
       

            Invoke-Command -Session $Sess -ScriptBlock{
            New-Item -Path 'C:\Data\temp\capacity' -ItemType Directory | out-null
            $hostname = hostname
            $path = "C:\Data\temp\capacity\$hostname"
           
            New-Item -Path $path -ItemType Directory | out-null
            $path += '\'

            Get-VMMemory * | select VMName, DynamicMemoryEnabled, Minimum, Startup, Maximum | Export-Csv $path'RamInfo.csv'
            
            Get-VMProcessor *| select VMname, Count | Export-Csv $path'CPUInfo.csv'
 
            Get-VMHardDiskDrive * | select Path | get-vhd | select Path, FileSize,Size  | Export-Csv $path'DiskInfo.csv'
			
			Get-vm * | select name, state | Export-Csv $path'StateInfo.csv'
            }
            
       
            Write-Verbose "Copying VM Data from $RemoteComputer" -Verbose

            $path = "C:\Data\temp\capacity\$RemoteComputer\"

            

            Copy-Item -FromSession $Sess $path -Destination $localPath -Recurse

            Write-Verbose "Deleting VM Data from $RemoteComputer" -Verbose

            Invoke-Command -Session $Sess -ScriptBlock{
            $path = "C:\Data\temp"
            Remove-Item $path -Force  -Recurse

            }

  


            Write-Verbose "$RemoteComputer : Closing PSSession" -Verbose
            Remove-PSSession -Session $Sess
        } else{
            Write-Verbose "$RemoteComputer : No action taken" -Verbose
        }
    }

    $localPath = "C:\Users\marks\IdeaProjects\ISNCapacity\staticinfo\"
    $date = Get-Date -UFormat "%m-%d-%Y-%R"
    New-item $localPath\timestamp.txt
    Set-content $localPath\timestamp.txt $date

}

end{


}