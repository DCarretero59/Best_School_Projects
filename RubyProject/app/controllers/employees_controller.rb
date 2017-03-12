class EmployeesController < ApplicationController
	def index
		if (params[:department_id])
			employees = Employee.joins(:departments).where(:departments => {:id => params[:department_id]}).first(30)
			# .joins(:salaries)
			render	json: employees
		else
			employees = Employee.last(100)
			render	json: employees
		end
	end

	def show
			employees = Employee.paginate(:page => params[:id], :per_page => 200)
			render	json: employees
	end

	def countEmployees
			conteo = Employee.count()
			conteo = conteo / 200
			conteo = conteo.to_i	
			render json: conteo
	end

	def createManager
			department = Department.find(params[:department_id])
			manager = Employee.find(params[:id])
			department.managers << manager
			render json: department.managers
	end

	def createEmployee
			department = Department.find(params[:department_id])
			employee = Employee.find(params[:id])
			department.employees << employee
			render json: department.employees
	end

	def create
		e = Employee.new
		e.first_name = params[:employee][:first_name]
		e.last_name = params[:employee][:last_name]
		e.birth_date = params[:employee][:birth_date]
		e.hire_date = params[:employee][:hire_date]
		e.gender = params[:employee][:gender]
		e.save
		render json: e
	end
end
