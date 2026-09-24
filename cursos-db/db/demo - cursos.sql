USE [demo]
GO
/****** Object:  User [briandev]    Script Date: 24/09/2026 10:13:06 ******/
CREATE USER [briandev] WITHOUT LOGIN WITH DEFAULT_SCHEMA=[dbo]
GO
/****** Object:  Table [dbo].[cursos]    Script Date: 24/09/2026 10:13:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[cursos](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[nombre] [nvarchar](150) NOT NULL,
	[instructor] [nvarchar](100) NOT NULL,
	[duracion_horas] [int] NOT NULL,
	[precio] [decimal](10, 2) NOT NULL,
	[activo] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[cursos] ADD  DEFAULT ((1)) FOR [activo]
GO


select * from [demo].[dbo].[cursos]