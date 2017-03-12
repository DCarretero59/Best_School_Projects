class Department < ActiveRecord::Base
	has_and_belongs_to_many :employees
	has_and_belongs_to_many :managers, class_name: "Employee", join_table: "departments_managers", :association_foreign_key => "manager_id"

	def employees_count
		self.employees.count
	end


	def as_json (opt = {}) 
		{
			id: self.id,
			name: self.name,
			employees_count: self.employees_count,
			managers: self.managers
		}
	end
end
